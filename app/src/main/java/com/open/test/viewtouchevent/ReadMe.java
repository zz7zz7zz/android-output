package com.open.test.viewtouchevent;

public class ReadMe {


/**

以下都是以return的日志，所以与Activity->Windwo->ViewGroup->View相反
默认如下：

 01-03 10:24:25.542 28914-28914/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_DOWN
 01-03 10:24:25.542 28914-28914/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: false ev: ACTION_DOWN
 01-03 10:24:25.542 28914-28914/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: false ev: ACTION_DOWN
 01-03 10:24:25.542 28914-28914/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: false   ev: ACTION_DOWN
 01-03 10:24:25.542 28914-28914/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: false   ev: ACTION_DOWN
 01-03 10:24:25.542 28914-28914/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: false    ev: ACTION_DOWN
 01-03 10:24:25.542 28914-28914/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: false    ev: ACTION_DOWN
 01-03 10:24:25.562 28914-28914/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: false    ev: ACTION_MOVE
 01-03 10:24:25.562 28914-28914/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: false    ev: ACTION_MOVE
 01-03 10:24:25.615 28914-28914/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: false    ev: ACTION_UP
 01-03 10:24:25.615 28914-28914/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: false    ev: ACTION_UP


 1.当View onTouchEvent 返回true时

 01-03 10:29:43.836 29805-29805/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_DOWN
 01-03 10:29:43.837 29805-29805/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: true ev: ACTION_DOWN
 01-03 10:29:43.837 29805-29805/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: true ev: ACTION_DOWN
 01-03 10:29:43.837 29805-29805/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_DOWN
 01-03 10:29:43.837 29805-29805/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_DOWN
 01-03 10:29:43.855 29805-29805/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_MOVE
 01-03 10:29:43.855 29805-29805/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: true ev: ACTION_MOVE
 01-03 10:29:43.856 29805-29805/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: true ev: ACTION_MOVE
 01-03 10:29:43.857 29805-29805/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_MOVE
 01-03 10:29:43.858 29805-29805/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_MOVE
 01-03 10:29:43.920 29805-29805/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_UP
 01-03 10:29:43.920 29805-29805/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: true ev: ACTION_UP
 01-03 10:29:43.920 29805-29805/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: true ev: ACTION_UP
 01-03 10:29:43.920 29805-29805/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_UP
 01-03 10:29:43.920 29805-29805/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_UP



 2.当View dispatchTouchEvent 返回true时

 01-03 10:31:22.168 29946-29946/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_DOWN
 01-03 10:31:22.168 29946-29946/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: false ev: ACTION_DOWN
 01-03 10:31:22.168 29946-29946/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: true ev: ACTION_DOWN
 01-03 10:31:22.168 29946-29946/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_DOWN
 01-03 10:31:22.169 29946-29946/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_DOWN
 01-03 10:31:22.188 29946-29946/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_MOVE
 01-03 10:31:22.188 29946-29946/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: false ev: ACTION_MOVE
 01-03 10:31:22.189 29946-29946/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: true ev: ACTION_MOVE
 01-03 10:31:22.189 29946-29946/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_MOVE
 01-03 10:31:22.190 29946-29946/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_MOVE
 01-03 10:31:22.265 29946-29946/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_UP
 01-03 10:31:22.265 29946-29946/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: false ev: ACTION_UP
 01-03 10:31:22.265 29946-29946/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: true ev: ACTION_UP
 01-03 10:31:22.265 29946-29946/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_UP
 01-03 10:31:22.265 29946-29946/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_UP

 20.当ViewGroup onTouchEvent 返回true时

 01-03 10:33:46.503 30139-30139/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_DOWN
 01-03 10:33:46.503 30139-30139/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: false ev: ACTION_DOWN
 01-03 10:33:46.503 30139-30139/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: false ev: ACTION_DOWN
 01-03 10:33:46.503 30139-30139/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: true   ev: ACTION_DOWN
 01-03 10:33:46.503 30139-30139/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_DOWN
 01-03 10:33:46.504 30139-30139/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_DOWN
 01-03 10:33:46.518 30139-30139/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: true   ev: ACTION_MOVE
 01-03 10:33:46.519 30139-30139/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_MOVE
 01-03 10:33:46.519 30139-30139/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_MOVE
 01-03 10:33:46.590 30139-30139/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: true   ev: ACTION_UP
 01-03 10:33:46.590 30139-30139/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_UP
 01-03 10:33:46.590 30139-30139/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_UP


 21.当ViewGroup dispatchTouchEvent 返回true时

 01-03 10:34:57.607 30284-30284/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_DOWN
 01-03 10:34:57.607 30284-30284/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: false ev: ACTION_DOWN
 01-03 10:34:57.607 30284-30284/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: false ev: ACTION_DOWN
 01-03 10:34:57.607 30284-30284/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: false   ev: ACTION_DOWN
 01-03 10:34:57.607 30284-30284/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_DOWN
 01-03 10:34:57.607 30284-30284/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_DOWN
 01-03 10:34:57.617 30284-30284/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: false   ev: ACTION_MOVE
 01-03 10:34:57.617 30284-30284/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_MOVE
 01-03 10:34:57.617 30284-30284/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_MOVE
 01-03 10:34:57.623 30284-30284/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: false   ev: ACTION_UP
 01-03 10:34:57.623 30284-30284/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_UP
 01-03 10:34:57.623 30284-30284/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_UP

 22.当ViewGroup viewGroup_return_onInterceptTouchEvent 返回true时

 01-03 10:37:47.450 30702-30702/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: true   ev: ACTION_DOWN
 01-03 10:37:47.450 30702-30702/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: false   ev: ACTION_DOWN
 01-03 10:37:47.450 30702-30702/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: false   ev: ACTION_DOWN
 01-03 10:37:47.451 30702-30702/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: false    ev: ACTION_DOWN
 01-03 10:37:47.451 30702-30702/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: false    ev: ACTION_DOWN
 01-03 10:37:47.469 30702-30702/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: false    ev: ACTION_MOVE
 01-03 10:37:47.470 30702-30702/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: false    ev: ACTION_MOVE
 01-03 10:37:47.504 30702-30702/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: false    ev: ACTION_UP
 01-03 10:37:47.504 30702-30702/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: false    ev: ACTION_UP

 23.当ViewGroup viewGroup_return_onInterceptTouchEvent 返回true时  && ViewGroup onTouchEvent 返回true时

 01-03 10:38:26.651 30820-30820/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: true   ev: ACTION_DOWN
 01-03 10:38:26.651 30820-30820/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: true   ev: ACTION_DOWN
 01-03 10:38:26.651 30820-30820/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_DOWN
 01-03 10:38:26.651 30820-30820/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_DOWN
 01-03 10:38:26.662 30820-30820/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: true   ev: ACTION_MOVE
 01-03 10:38:26.662 30820-30820/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_MOVE
 01-03 10:38:26.662 30820-30820/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_MOVE
 01-03 10:38:26.721 30820-30820/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: true   ev: ACTION_UP
 01-03 10:38:26.721 30820-30820/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_UP
 01-03 10:38:26.721 30820-30820/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_UP

 23.当ViewGroup viewGroup_return_onInterceptTouchEvent 返回true时  && ViewGroup dispatchTouchEvent 返回true时

 01-03 10:40:26.921 31078-31078/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: true   ev: ACTION_DOWN
 01-03 10:40:26.921 31078-31078/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: false   ev: ACTION_DOWN
 01-03 10:40:26.921 31078-31078/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_DOWN
 01-03 10:40:26.921 31078-31078/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_DOWN
 01-03 10:40:26.939 31078-31078/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: false   ev: ACTION_MOVE
 01-03 10:40:26.939 31078-31078/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_MOVE
 01-03 10:40:26.939 31078-31078/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_MOVE
 01-03 10:40:26.985 31078-31078/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: false   ev: ACTION_UP
 01-03 10:40:26.985 31078-31078/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: true   ev: ACTION_UP
 01-03 10:40:26.985 31078-31078/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_UP


 30.当Activity onTouchEvent 返回true时
 01-03 10:41:36.541 31222-31222/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_DOWN
 01-03 10:41:36.541 31222-31222/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: false ev: ACTION_DOWN
 01-03 10:41:36.541 31222-31222/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: false ev: ACTION_DOWN
 01-03 10:41:36.541 31222-31222/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: false   ev: ACTION_DOWN
 01-03 10:41:36.541 31222-31222/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: false   ev: ACTION_DOWN
 01-03 10:41:36.541 31222-31222/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: true    ev: ACTION_DOWN
 01-03 10:41:36.541 31222-31222/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_DOWN
 01-03 10:41:36.558 31222-31222/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: true    ev: ACTION_MOVE
 01-03 10:41:36.558 31222-31222/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_MOVE
 01-03 10:41:36.622 31222-31222/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: true    ev: ACTION_UP
 01-03 10:41:36.622 31222-31222/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_UP


 31.当Activity dispatchTouchEvent 返回true时
 01-03 10:42:55.108 31364-31364/com.open.iandroidtsing V/Event: ViewGroup: onInterceptTouchEvent    ret: false   ev: ACTION_DOWN
 01-03 10:42:55.108 31364-31364/com.open.iandroidtsing V/Event: View: onTouchEvent         ret: false ev: ACTION_DOWN
 01-03 10:42:55.108 31364-31364/com.open.iandroidtsing V/Event: View: dispatchTouchEvent   ret: false ev: ACTION_DOWN
 01-03 10:42:55.108 31364-31364/com.open.iandroidtsing V/Event: ViewGroup: onTouchEvent             ret: false   ev: ACTION_DOWN
 01-03 10:42:55.109 31364-31364/com.open.iandroidtsing V/Event: ViewGroup: dispatchTouchEvent       ret: false   ev: ACTION_DOWN
 01-03 10:42:55.109 31364-31364/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: false    ev: ACTION_DOWN
 01-03 10:42:55.109 31364-31364/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_DOWN
 01-03 10:42:55.131 31364-31364/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: false    ev: ACTION_MOVE
 01-03 10:42:55.131 31364-31364/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_MOVE
 01-03 10:42:55.172 31364-31364/com.open.iandroidtsing V/Event: Activity: onTouchEvent         ret: false    ev: ACTION_UP
 01-03 10:42:55.172 31364-31364/com.open.iandroidtsing V/Event: Activity: dispatchTouchEvent   ret: true    ev: ACTION_UP



 */



}
