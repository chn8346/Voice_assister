package com.example.phoneui;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;


/**
 * This class demonstrates how an accessibility service can query
 * window content to improve the feedback given to the user.
 */
public class blind_server extends AccessibilityService{

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {

    }

    @Override
    public void onInterrupt() {

    }
}