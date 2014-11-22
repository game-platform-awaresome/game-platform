package com.babeeta.butterfly.testkit.server.rest;

import com.babeeta.butterfly.testkit.server.rest.app.account.GameAccountClient;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 */
public class TestGame {

    private String domain = "com.future.gameplatform.account.game.2066.cn";

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGame(){
        GamePlatformClient gamePlatformClient = new GamePlatformClient(domain, 8090);

        gamePlatformClient.testCreateGame("老虎机","http://resource.2066.cn:8090/pkg/SlotMachine_release_000000.apk",
                "http://resource.2066.cn:8090/img/2013/main_laohuji.png",
                "com.playgames.slotmachine","1","banner");
        gamePlatformClient.testCreateGame("斗地主","http://resource.2066.cn:8090/pkg/%E6%96%97%E5%9C%B0%E4%B8%BB.apk",
                "http://resource.2066.cn:8090/img/2013/main_doudizhu.png",
                "com.mas.wawagame.BDDKlord","3421","banner");
        gamePlatformClient.testCreateGame("开心麻将","http://resource.2066.cn:8090/pkg/%E9%BA%BB%E5%B0%86.apk",
                "http://resource.2066.cn:8090/img/2013/main_majiang.png",
                "com.shaaxifengyun.lty2.gbmj","1","banner");
        gamePlatformClient.testCreateGame("扎金花","http://resource.2066.cn:8090/pkg/%E7%82%B8%E9%87%91%E8%8A%B1.apk",
                "http://resource.2066.cn:8090/img/2013/main_zajinhua.png",
                "com.zjh.cmge","5","banner");
        /**
        gamePlatformClient.testCreateGame("我叫MT","http://resource.2066.cn:8090/pkg/IAMMT.apk",
                "http://resource.2066.cn:8090/img/2013/%E6%88%91%E5%8F%ABMT.png",
                "com.locojoy.immt_a_chs","1600","net");
        gamePlatformClient.testCreateGame("君王2","http://resource.2066.cn:8090/pkg/junwang2.apk",
                "http://resource.2066.cn:8090/img/2013/%E5%90%9B%E7%8E%8B.png",
                "com.king2.mf","5","net");

        gamePlatformClient.testCreateGame("萌江湖","http://resource.2066.cn:8090/pkg/mengjianghu.apk",
                "http://resource.2066.cn:8090/img/2013/%E8%90%8C%E6%B1%9F%E6%B9%96.png",
                "air.com.uuzu.mjhapk.uuzu","1006000","net");

        gamePlatformClient.testCreateGame("摩卡幻想","http://resource.2066.cn:8090/pkg/mokahuanxiang.apk",
                "http://resource.2066.cn:8090/img/2013/%E9%AD%94%E5%8D%A1%E5%B9%BB%E6%83%B3.png",
                "air.com.ifree.cardCHS","1002000","net");

        gamePlatformClient.testCreateGame("神仙道","http://resource.2066.cn:8090/pkg/shenxiandao.apk",
                "http://resource.2066.cn:8090/img/2013/%E7%A5%9E%E4%BB%99%E9%81%93.png",
                "com.pinidea.ios.sxd","3150","net");

        gamePlatformClient.testCreateGame("世界ol","http://resource.2066.cn:8090/pkg/shijieOL.apk",
                "http://resource.2066.cn:8090/img/2013/%E4%B8%96%E7%95%8COL.png",
                "com.lori.app","200","net");

        gamePlatformClient.testCreateGame("时空猎人","http://resource.2066.cn:8090/pkg/shikongleiren.apk",
                "http://resource.2066.cn:8090/img/2013/%E6%97%B6%E7%A9%BA%E7%8C%8E%E4%BA%BA.png",
                "com.yinhan.hunter.yh","15","net");

        gamePlatformClient.testCreateGame("忘仙","http://resource.2066.cn:8090/pkg/wangxian.apk",
                "http://resource.2066.cn:8090/img/2013/%E5%BF%98%E4%BB%99.png",
                "org.cocos2dx.tests","263","net");

        gamePlatformClient.testCreateGame("王者之剑","http://resource.2066.cn:8090/pkg/wangzhezhijian.apk",
                "http://resource.2066.cn:8090/img/2013/%E7%8E%8B%E8%80%85%E4%B9%8B%E5%89%91.png",
                "com.lk.arthur","6","net");

        gamePlatformClient.testCreateGame("小小帝国","http://resource.2066.cn:8090/pkg/xiaoxiaodiguo.apk",
                "http://resource.2066.cn:8090/img/2013/%E5%B0%8F%E5%B0%8F%E5%B8%9D%E5%9B%BD.png",
                "com.camelgames.fantasyland_rongtuo","79","net");

        gamePlatformClient.testCreateGame("保卫萝卜","http://resource.2066.cn:8090/pkg/baoweiluobo240.apk",
                "http://resource.2066.cn:8090/img/2013/%E4%BF%9D%E5%8D%AB%E8%90%9D%E5%8D%9C.png",
                "com.carrot.carrotfantasy","240","stand");
        gamePlatformClient.testCreateGame("小鳄鱼爱洗澡","http://resource.2066.cn:8090/pkg/eyuxiaowanpiaixizao_v1.10.0_20130314.apk",
                "http://resource.2066.cn:8090/img/2013/%E5%B0%8F%E9%B3%84%E9%B1%BC%E7%88%B1%E6%B4%97%E6%BE%A1.png",
                "com.disney.chukong.WMW","17","stand");
        gamePlatformClient.testCreateGame("捕鱼达人2","http://resource.2066.cn:8090/pkg/fishingjoy2.apk",
                "http://resource.2066.cn:8090/img/2013/%E6%8D%95%E9%B1%BC%E8%BE%BE%E4%BA%BA.png",
                "org.cocos2dx.FishingJoy2","75","stand");
        gamePlatformClient.testCreateGame("滑雪大冒险","http://resource.2066.cn:8090/pkg/huaxuedamaoxian.apk",
                "http://resource.2066.cn:8090/img/2013/%E6%BB%91%E9%9B%AA%E5%A4%A7%E5%86%92%E9%99%A9.png",
                "com.yodo1tier1.ido360.SkiSafari","1003","stand");
        gamePlatformClient.testCreateGame("雷电2012","http://resource.2066.cn:8090/pkg/leidian2012HD.apk",
                "http://resource.2066.cn:8090/img/2013/%E9%9B%B7%E7%94%B52013.png",
                "cn.koogame.Fighter","29","stand");



        gamePlatformClient.testCreateGame("燃烧的蔬菜","http://resource.2066.cn:8090/pkg/ranshaodeshucai.apk",
                "http://resource.2066.cn:8090/img/2013/%E7%87%83%E7%83%A7%E7%9A%84%E8%94%AC%E8%8F%9C.png",
                "com.soco.growaway_10086","404","stand");
        gamePlatformClient.testCreateGame("神庙逃亡","http://resource.2066.cn:8090/pkg/TempleRun1.apk",
                "http://resource.2066.cn:8090/img/2013/%E7%A5%9E%E5%BA%99%E9%80%83%E4%BA%A12.png",
                "com.imangi.templerunzh","13","stand");
        gamePlatformClient.testCreateGame("3D狂飙赛车","http://resource.2066.cn:8090/pkg/webxo_CarFly3D_v1.apk",
                "http://resource.2066.cn:8090/img/2013/3D%E7%8B%82%E9%A3%99%E8%B5%9B%E8%BD%A6.png",
                "com.sxiaoao.car3d","57","stand");
        gamePlatformClient.testCreateGame("找你妹","http://resource.2066.cn:8090/pkg/zhaonimei.apk",
                "http://resource.2066.cn:8090/img/2013/%E6%89%BE%E4%BD%A0%E5%A6%B9.png",
                "org.funship.findsomething.withRK","16","stand");
        gamePlatformClient.testCreateGame("植物大战僵尸无尽版","http://resource.2066.cn:8090/pkg/zhiwudazhanjiangshi.apk",
                "http://resource.2066.cn:8090/img/2013/%E6%A4%8D%E7%89%A9%E5%A4%A7%E6%88%98%E5%83%B5%E5%B0%B8.png",
                "com.popcap.pvzthirdwvga","221","stand");




        gamePlatformClient.testCreateGame("好玩游戏","http://resource.2066.cn:8090/pkg/2066.apk",
                "http://resource.2066.cn:8090/img/2013/gamehouse2066.png",
                "com.air.gamehouse","1","gamehouse");




        Map[] info = new HashMap[2];
        Map item1 = new HashMap();
        item1.put("cid","000000");
        item1.put("cname","自有渠道");
        item1.put("downuri","http://resource.2066.cn:8090/pkg/2066.apk");
        item1.put("picuri","http://resource.2066.cn:8090/img/2013/gamehouse2066.png");
        item1.put("pkgname","com.air.gamehouse");
        item1.put("version","1");
        item1.put("update","casual");
        info[0] = item1;

        Map item2 = new HashMap();
        item2.put("cid","000001");
        item2.put("cname","测试渠道");
        item2.put("downuri","http://resource.2066.cn:8090/pkg/2066.apk");
        item2.put("picuri","http://resource.2066.cn:8090/img/2013/gamehouse2066.png");
        item2.put("pkgname","com.air.gamehouse");
        item2.put("version","2");
        item2.put("update","casual");
        info[1] = item2;

        gamePlatformClient.testUpdateGameHouse("51c1c1c69679b76072a2622a92d2706e","好玩游戏","http://resource.2066.cn:8090/pkg/2066.apk",
                "http://resource.2066.cn:8090/img/2013/gamehouse2066.png",
                "com.air.gamehouse","1","gamehouse", info);

        String result = null;
        try {
            result = EntityUtils
                    .toString(gamePlatformClient.getResponseEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        gamePlatformClient.testVersion("000000","1");
        String result = null;
        try {
            result = EntityUtils
                    .toString(gamePlatformClient.getResponseEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
         **/
        gamePlatformClient.testList();
        String result = null;
        try {
            result = EntityUtils
                    .toString(gamePlatformClient.getResponseEntity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result = " + result );
    }

    @After
    public void tearDown() throws Exception {
    }
}
