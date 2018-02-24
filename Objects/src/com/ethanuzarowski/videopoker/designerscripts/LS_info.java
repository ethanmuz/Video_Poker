package com.ethanuzarowski.videopoker.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_info{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
anywheresoftware.b4a.keywords.LayoutBuilder.scaleAll(views);
views.get("infoback").vw.setLeft((int)((10d / 100 * width)));
views.get("infoback").vw.setWidth((int)((35d / 100 * width) - ((10d / 100 * width))));
views.get("infoback").vw.setTop((int)((10d / 100 * height)));
views.get("infoback").vw.setHeight((int)((30d / 100 * height) - ((10d / 100 * height))));
views.get("infolabel").vw.setLeft((int)((20d / 100 * width)));
views.get("infolabel").vw.setWidth((int)((80d / 100 * width) - ((20d / 100 * width))));
views.get("infolabel").vw.setTop((int)((40d / 100 * height)));
views.get("infolabel").vw.setHeight((int)((90d / 100 * height) - ((40d / 100 * height))));

}
}