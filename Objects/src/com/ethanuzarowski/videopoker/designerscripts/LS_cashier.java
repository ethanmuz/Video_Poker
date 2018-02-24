package com.ethanuzarowski.videopoker.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_cashier{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
//BA.debugLineNum = 2;BA.debugLine="AutoScaleAll"[cashier/General script]
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
//BA.debugLineNum = 3;BA.debugLine="cashierback.SetLeftAndRight(10%x,35%x)"[cashier/General script]
views.get("cashierback").vw.setLeft((int)((10d / 100 * width)));
views.get("cashierback").vw.setWidth((int)((35d / 100 * width) - ((10d / 100 * width))));
//BA.debugLineNum = 4;BA.debugLine="cashierback.SetTopAndBottom(10%y,30%y)"[cashier/General script]
views.get("cashierback").vw.setTop((int)((10d / 100 * height)));
views.get("cashierback").vw.setHeight((int)((30d / 100 * height) - ((10d / 100 * height))));
//BA.debugLineNum = 5;BA.debugLine="cashierbalance.SetLeftAndRight(20%x,80%x)"[cashier/General script]
views.get("cashierbalance").vw.setLeft((int)((20d / 100 * width)));
views.get("cashierbalance").vw.setWidth((int)((80d / 100 * width) - ((20d / 100 * width))));
//BA.debugLineNum = 6;BA.debugLine="cashierbalance.SetTopAndBottom(40%y,60%y)"[cashier/General script]
views.get("cashierbalance").vw.setTop((int)((40d / 100 * height)));
views.get("cashierbalance").vw.setHeight((int)((60d / 100 * height) - ((40d / 100 * height))));
//BA.debugLineNum = 7;BA.debugLine="cashierreup.SetLeftAndRight(20%x,80%x)"[cashier/General script]
views.get("cashierreup").vw.setLeft((int)((20d / 100 * width)));
views.get("cashierreup").vw.setWidth((int)((80d / 100 * width) - ((20d / 100 * width))));
//BA.debugLineNum = 8;BA.debugLine="cashierreup.SetTopAndBottom(65%y,90%y)"[cashier/General script]
views.get("cashierreup").vw.setTop((int)((65d / 100 * height)));
views.get("cashierreup").vw.setHeight((int)((90d / 100 * height) - ((65d / 100 * height))));

}
}