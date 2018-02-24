package com.ethanuzarowski.videopoker.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_menu{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("menuimg").vw.setTop((int)((0d / 100 * height)));
views.get("menuimg").vw.setHeight((int)((25d / 100 * height)));
views.get("menuimg").vw.setLeft((int)((0d / 100 * width)));
views.get("menuimg").vw.setWidth((int)((100d / 100 * width)));
views.get("menuscroller").vw.setLeft((int)((0d / 100 * width)));
views.get("menuscroller").vw.setWidth((int)((100d / 100 * width)));
views.get("menuscroller").vw.setHeight((int)((50d / 100 * height)));
views.get("menuscroller").vw.setTop((int)((25d / 100 * height)));
views.get("menuinfo").vw.setLeft((int)((0d / 100 * width)));
views.get("menuinfo").vw.setWidth((int)((30d / 100 * width)));
views.get("menuinfo").vw.setHeight((int)((25d / 100 * height)));
views.get("menuinfo").vw.setTop((int)((75d / 100 * height)));
views.get("menusettings").vw.setLeft((int)((35d / 100 * width)));
views.get("menusettings").vw.setWidth((int)((30d / 100 * width)));
views.get("menusettings").vw.setHeight((int)((25d / 100 * height)));
views.get("menusettings").vw.setTop((int)((75d / 100 * height)));
views.get("menucashier").vw.setLeft((int)((70d / 100 * width)));
views.get("menucashier").vw.setWidth((int)((30d / 100 * width)));
views.get("menucashier").vw.setHeight((int)((25d / 100 * height)));
views.get("menucashier").vw.setTop((int)((75d / 100 * height)));
views.get("pnljacksorbetter").vw.setHeight((int)((25d / 100 * height)));
views.get("pnljacksorbetter").vw.setTop((int)((130d / 100 * height)));
views.get("pnlallamerican").vw.setHeight((int)((25d / 100 * height)));
views.get("pnlallamerican").vw.setTop((int)((130d / 100 * height)));
views.get("background").vw.setLeft((int)(0-(10d / 100 * width)));
views.get("background").vw.setTop((int)(0-(10d / 100 * height)));
views.get("background").vw.setWidth((int)((120d / 100 * width)));
views.get("background").vw.setHeight((int)((120d / 100 * height)));
views.get("pnlscrollforgames").vw.setHeight((int)((25d / 100 * height)));
views.get("pnlscrollforgames").vw.setTop((int)((130d / 100 * height)));
views.get("pnlmoregamescomingsoon").vw.setHeight((int)((25d / 100 * height)));
views.get("pnlmoregamescomingsoon").vw.setTop((int)((130d / 100 * height)));

}
}