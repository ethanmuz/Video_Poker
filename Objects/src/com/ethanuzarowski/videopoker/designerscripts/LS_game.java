package com.ethanuzarowski.videopoker.designerscripts;
import anywheresoftware.b4a.objects.TextViewWrapper;
import anywheresoftware.b4a.objects.ImageViewWrapper;
import anywheresoftware.b4a.BA;


public class LS_game{

public static void LS_general(java.util.LinkedHashMap<String, anywheresoftware.b4a.keywords.LayoutBuilder.ViewWrapperAndAnchor> views, int width, int height, float scale) {
anywheresoftware.b4a.keywords.LayoutBuilder.setScaleRate(0.3);
views.get("paytable").vw.setTop((int)((5d / 100 * height)));
views.get("paytable").vw.setLeft((int)((5d / 100 * width)));
views.get("paytable").vw.setWidth((int)((70d / 100 * width)));
views.get("paytable").vw.setHeight((int)((35d / 100 * height)));
views.get("card1").vw.setTop((int)((50d / 100 * height)));
views.get("card2").vw.setTop((int)((50d / 100 * height)));
//BA.debugLineNum = 8;BA.debugLine="card3.Top = 50%y"[game/General script]
views.get("card3").vw.setTop((int)((50d / 100 * height)));
//BA.debugLineNum = 9;BA.debugLine="card4.Top = 50%y"[game/General script]
views.get("card4").vw.setTop((int)((50d / 100 * height)));
//BA.debugLineNum = 10;BA.debugLine="card5.Top = 50%y"[game/General script]
views.get("card5").vw.setTop((int)((50d / 100 * height)));
//BA.debugLineNum = 11;BA.debugLine="card1.Height = 30%y"[game/General script]
views.get("card1").vw.setHeight((int)((30d / 100 * height)));
//BA.debugLineNum = 12;BA.debugLine="card2.Height = 30%y"[game/General script]
views.get("card2").vw.setHeight((int)((30d / 100 * height)));
//BA.debugLineNum = 13;BA.debugLine="card3.Height = 30%y"[game/General script]
views.get("card3").vw.setHeight((int)((30d / 100 * height)));
//BA.debugLineNum = 14;BA.debugLine="card4.Height = 30%y"[game/General script]
views.get("card4").vw.setHeight((int)((30d / 100 * height)));
//BA.debugLineNum = 15;BA.debugLine="card5.Height = 30%y"[game/General script]
views.get("card5").vw.setHeight((int)((30d / 100 * height)));
//BA.debugLineNum = 16;BA.debugLine="card1.Left = 5%x"[game/General script]
views.get("card1").vw.setLeft((int)((5d / 100 * width)));
//BA.debugLineNum = 17;BA.debugLine="card2.Left = 19.5%x"[game/General script]
views.get("card2").vw.setLeft((int)((19.5d / 100 * width)));
//BA.debugLineNum = 18;BA.debugLine="card3.Left = 34%x"[game/General script]
views.get("card3").vw.setLeft((int)((34d / 100 * width)));
//BA.debugLineNum = 19;BA.debugLine="card4.Left = 48.5%x"[game/General script]
views.get("card4").vw.setLeft((int)((48.5d / 100 * width)));
//BA.debugLineNum = 20;BA.debugLine="card5.Left = 63%x"[game/General script]
views.get("card5").vw.setLeft((int)((63d / 100 * width)));
//BA.debugLineNum = 21;BA.debugLine="card1.Width=12%x"[game/General script]
views.get("card1").vw.setWidth((int)((12d / 100 * width)));
//BA.debugLineNum = 22;BA.debugLine="card2.Width=12%x"[game/General script]
views.get("card2").vw.setWidth((int)((12d / 100 * width)));
//BA.debugLineNum = 23;BA.debugLine="card3.Width=12%x"[game/General script]
views.get("card3").vw.setWidth((int)((12d / 100 * width)));
//BA.debugLineNum = 24;BA.debugLine="card4.Width=12%x"[game/General script]
views.get("card4").vw.setWidth((int)((12d / 100 * width)));
//BA.debugLineNum = 25;BA.debugLine="card5.Width=12%x"[game/General script]
views.get("card5").vw.setWidth((int)((12d / 100 * width)));
//BA.debugLineNum = 26;BA.debugLine="lblresult.Top=80%y"[game/General script]
views.get("lblresult").vw.setTop((int)((80d / 100 * height)));
//BA.debugLineNum = 27;BA.debugLine="lblresult.Height=10%y"[game/General script]
views.get("lblresult").vw.setHeight((int)((10d / 100 * height)));
//BA.debugLineNum = 28;BA.debugLine="lblresult.Left = 5%x"[game/General script]
views.get("lblresult").vw.setLeft((int)((5d / 100 * width)));
//BA.debugLineNum = 29;BA.debugLine="lblresult.Width = 70%x"[game/General script]
views.get("lblresult").vw.setWidth((int)((70d / 100 * width)));
//BA.debugLineNum = 30;BA.debugLine="backbutton.Top = 5%y"[game/General script]
views.get("backbutton").vw.setTop((int)((5d / 100 * height)));
//BA.debugLineNum = 31;BA.debugLine="backbutton.Left = 77.5%x"[game/General script]
views.get("backbutton").vw.setLeft((int)((77.5d / 100 * width)));
//BA.debugLineNum = 32;BA.debugLine="backbutton.Width = 17.5%x"[game/General script]
views.get("backbutton").vw.setWidth((int)((17.5d / 100 * width)));
//BA.debugLineNum = 33;BA.debugLine="backbutton.Height = 25%y"[game/General script]
views.get("backbutton").vw.setHeight((int)((25d / 100 * height)));
//BA.debugLineNum = 34;BA.debugLine="lblbet.Top = 35%y"[game/General script]
views.get("lblbet").vw.setTop((int)((35d / 100 * height)));
//BA.debugLineNum = 35;BA.debugLine="lblbet.Height = 25%y"[game/General script]
views.get("lblbet").vw.setHeight((int)((25d / 100 * height)));
//BA.debugLineNum = 36;BA.debugLine="lblbet.Left = 77.5%x"[game/General script]
views.get("lblbet").vw.setLeft((int)((77.5d / 100 * width)));
//BA.debugLineNum = 37;BA.debugLine="lblbet.Width = 17.5%x"[game/General script]
views.get("lblbet").vw.setWidth((int)((17.5d / 100 * width)));
//BA.debugLineNum = 38;BA.debugLine="Draw.Top = 65%y"[game/General script]
views.get("draw").vw.setTop((int)((65d / 100 * height)));
//BA.debugLineNum = 39;BA.debugLine="Draw.Height = 25%y"[game/General script]
views.get("draw").vw.setHeight((int)((25d / 100 * height)));
//BA.debugLineNum = 40;BA.debugLine="Draw.Left = 77.5%x"[game/General script]
views.get("draw").vw.setLeft((int)((77.5d / 100 * width)));
//BA.debugLineNum = 41;BA.debugLine="Draw.Width = 17.5%x"[game/General script]
views.get("draw").vw.setWidth((int)((17.5d / 100 * width)));
//BA.debugLineNum = 42;BA.debugLine="lblbalance.Top=40%y"[game/General script]
views.get("lblbalance").vw.setTop((int)((40d / 100 * height)));
//BA.debugLineNum = 43;BA.debugLine="lblbalance.Height=10%y"[game/General script]
views.get("lblbalance").vw.setHeight((int)((10d / 100 * height)));
//BA.debugLineNum = 44;BA.debugLine="lblbalance.Left = 5%x"[game/General script]
views.get("lblbalance").vw.setLeft((int)((5d / 100 * width)));
//BA.debugLineNum = 45;BA.debugLine="lblbalance.Width = 70%x"[game/General script]
views.get("lblbalance").vw.setWidth((int)((70d / 100 * width)));
//BA.debugLineNum = 46;BA.debugLine="Redraw.Top = 65%y"[game/General script]
views.get("redraw").vw.setTop((int)((65d / 100 * height)));
//BA.debugLineNum = 47;BA.debugLine="Redraw.Height = 25%y"[game/General script]
views.get("redraw").vw.setHeight((int)((25d / 100 * height)));
//BA.debugLineNum = 48;BA.debugLine="Redraw.Left = 77.5%x"[game/General script]
views.get("redraw").vw.setLeft((int)((77.5d / 100 * width)));
//BA.debugLineNum = 49;BA.debugLine="Redraw.Width = 17.5%x"[game/General script]
views.get("redraw").vw.setWidth((int)((17.5d / 100 * width)));
//BA.debugLineNum = 50;BA.debugLine="hold1.Top = 60%y"[game/General script]
views.get("hold1").vw.setTop((int)((60d / 100 * height)));
//BA.debugLineNum = 51;BA.debugLine="hold2.Top = 60%y"[game/General script]
views.get("hold2").vw.setTop((int)((60d / 100 * height)));
//BA.debugLineNum = 52;BA.debugLine="hold3.Top = 60%y"[game/General script]
views.get("hold3").vw.setTop((int)((60d / 100 * height)));
//BA.debugLineNum = 53;BA.debugLine="hold4.Top = 60%y"[game/General script]
views.get("hold4").vw.setTop((int)((60d / 100 * height)));
//BA.debugLineNum = 54;BA.debugLine="hold5.Top = 60%y"[game/General script]
views.get("hold5").vw.setTop((int)((60d / 100 * height)));
//BA.debugLineNum = 55;BA.debugLine="hold1.Height = 10%y"[game/General script]
views.get("hold1").vw.setHeight((int)((10d / 100 * height)));
//BA.debugLineNum = 56;BA.debugLine="hold2.Height = 10%y"[game/General script]
views.get("hold2").vw.setHeight((int)((10d / 100 * height)));
//BA.debugLineNum = 57;BA.debugLine="hold3.Height = 10%y"[game/General script]
views.get("hold3").vw.setHeight((int)((10d / 100 * height)));
//BA.debugLineNum = 58;BA.debugLine="hold4.Height = 10%y"[game/General script]
views.get("hold4").vw.setHeight((int)((10d / 100 * height)));
//BA.debugLineNum = 59;BA.debugLine="hold5.Height = 10%y"[game/General script]
views.get("hold5").vw.setHeight((int)((10d / 100 * height)));
//BA.debugLineNum = 60;BA.debugLine="hold1.Left = 5%x"[game/General script]
views.get("hold1").vw.setLeft((int)((5d / 100 * width)));
//BA.debugLineNum = 61;BA.debugLine="hold2.Left = 19.5%x"[game/General script]
views.get("hold2").vw.setLeft((int)((19.5d / 100 * width)));
//BA.debugLineNum = 62;BA.debugLine="hold3.Left = 34%x"[game/General script]
views.get("hold3").vw.setLeft((int)((34d / 100 * width)));
//BA.debugLineNum = 63;BA.debugLine="hold4.Left = 48.5%x"[game/General script]
views.get("hold4").vw.setLeft((int)((48.5d / 100 * width)));
//BA.debugLineNum = 64;BA.debugLine="hold5.Left = 63%x"[game/General script]
views.get("hold5").vw.setLeft((int)((63d / 100 * width)));
//BA.debugLineNum = 65;BA.debugLine="hold1.Width=12%x"[game/General script]
views.get("hold1").vw.setWidth((int)((12d / 100 * width)));
//BA.debugLineNum = 66;BA.debugLine="hold2.Width=12%x"[game/General script]
views.get("hold2").vw.setWidth((int)((12d / 100 * width)));
//BA.debugLineNum = 67;BA.debugLine="hold3.Width=12%x"[game/General script]
views.get("hold3").vw.setWidth((int)((12d / 100 * width)));
//BA.debugLineNum = 68;BA.debugLine="hold4.Width=12%x"[game/General script]
views.get("hold4").vw.setWidth((int)((12d / 100 * width)));
//BA.debugLineNum = 69;BA.debugLine="hold5.Width=12%x"[game/General script]
views.get("hold5").vw.setWidth((int)((12d / 100 * width)));

}
}