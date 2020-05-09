package com.example.uncledrew.aidltestservice;
import com.example.uncledrew.aidltestservice.ICallbackInterface;
interface aidlTest{
    void first(int a);
    void second(int b);
    void initMediaPlayer();

    void addPlayCallback(ICallbackInterface iCallbackInterface);
    void removePlayCallback(ICallbackInterface iCallbackInterface);
}
