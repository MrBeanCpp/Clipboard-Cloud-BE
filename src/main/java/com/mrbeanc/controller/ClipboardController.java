package com.mrbeanc.controller;

import com.mrbeanc.model.Clipboard;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ClipboardController {
    Map<String, Clipboard> clips = new ConcurrentHashMap<>();
    Map<String, DeferredResult<Clipboard>> waitlist = new ConcurrentHashMap<>();

    /**长轮询，如果是HOOK Ctrl+V再去获取的话，可能会有延时，体验很差<br>
     * 若服务端掉线，HTTP会自动重连，重新进入这个方法，重新注册一次，所以不用担心push失败
     */
    @GetMapping("/clipboard/long-polling/{id}/{os}") //for Windows
    public DeferredResult<Clipboard> clipboard_long_polling(@PathVariable String id, @PathVariable String os) {
        DeferredResult<Clipboard> deferResult = new DeferredResult<>(60_000L); //60s
        // 包含所有完成状态（正常、超时、异常），集中处理
        // 这对于检测 DeferredResult 实例不再可用非常有用
        deferResult.onCompletion(() -> { // 完成后从waitlist中移除
            if (waitlist.containsKey(id) && waitlist.get(id).equals(deferResult)) { //确认存储的是该对象
                waitlist.remove(id);
                // 超时后若setErrorResult，则不会抛出异常，但是会返回状态码200，不符合预期
                // 故不设置errorResult，而是在全局异常处理中捕获AsyncRequestTimeoutException，返回状态码304
            }
        });

        //如果不消费(remove)数据，就无法判断是否新数据，推送可能重复或失败（轮询间隔时更新）
        //因此目前只支持 单生产者 单消费者（符合日常使用）
        if (clips.containsKey(id) && !clips.get(id).getOs().equals(os)) { //不能消费自己推送的数据
            deferResult.setResult(clips.remove(id));
        } else {
            if (waitlist.containsKey(id)) {
                waitlist.get(id).setErrorResult("肿么会同时发起多个长轮询！！"); // OnComplete中统一移除
            }
            waitlist.put(id, deferResult);
        }
        return deferResult;
    }

    @GetMapping("/clipboard/{id}/{os}") // for IOS
    public Clipboard clipboard(@PathVariable String id, @PathVariable String os) {
        if (clips.containsKey(id) && !clips.get(id).getOs().equals(os)) { //不能消费自己推送的数据
            return clips.remove(id);
        } else {
            return new Clipboard();
        }
    }

    @PostMapping("/clipboard/{id}/{os}")
    @ResponseStatus(HttpStatus.OK)
    public void copyToCloud(@PathVariable String id, @PathVariable String os, @RequestBody Clipboard clipboard) {
        clipboard.setOs(os);
        //目前仅支持单Windows + 单IOS，后续可以加入设备ID区分不同Windows
        if (waitlist.containsKey(id) && os.equals("ios")) { //仅IOS向Windows推送， IOS仅快捷指令手动Get
            waitlist.get(id).setResult(clipboard); //通知长轮询
            System.out.println("Push to " + id);
        } else
            this.clips.put(id, clipboard);
        System.out.println(id + " : " + clipboard);
    }
}
