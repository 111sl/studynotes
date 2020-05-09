package com.example.uncledrew.aidltestservice;
import com.example.uncledrew.aidltestservice.ICallbackInterface;
interface aidlTest{
    void play(int state);
    void pause(int state);
    void initMediaPlayer();

    void addPlayCallback(ICallbackInterface iCallbackInterface);
    void removePlayCallback(ICallbackInterface iCallbackInterface);
}
