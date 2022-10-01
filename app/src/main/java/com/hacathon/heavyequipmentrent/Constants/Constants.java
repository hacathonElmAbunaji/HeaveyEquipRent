package com.hacathon.heavyequipmentrent.Constants;

import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.ui.LoginFragment;

public class Constants {

    public enum ShowOrHide{
        SHOW,
        HIDE
    }


    public enum Navigations{
        Register,
        Login,
        Home,
        SUB_CATEGORY,
        SELECT_EQUIPMENT,
        CONTINUE_ORDER,
        Orders,
        Settings,
        Profile
    }

    public static String IS_FIRSTRUN = "IS_FIRSTRUN";



    public static Integer NEW = 0;
    public static Integer COMPLETED = 1;
    public static Integer DELETED = -1;
    public static Integer EXPIRED = -2;





}
