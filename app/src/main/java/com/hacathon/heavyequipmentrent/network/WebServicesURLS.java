package com.hacathon.heavyequipmentrent.network;

import com.hacathon.heavyequipmentrent.BuildConfig;

public class WebServicesURLS {

    public final static  String BaseDomain = BuildConfig.Base_Domain;


    public final static String KWebLogin = BaseDomain + "login";
    public final static String KWebCategories = BaseDomain + "categories";
    public final static String KWebSubCategories = BaseDomain + "subCategories";
    public final static String KWebEquipments = BaseDomain + "equipments";
    public final static String KWebCreateOrder = BaseDomain + "createRFP";
    public final static String KWebGetMyRFPs = BaseDomain + "getMyRFPs";
    public final static String KWebRateProposal = BaseDomain + "rateProposal";

}
