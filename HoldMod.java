package com.apple01488.hold;

import com.sun.jna.platform.unix.X11;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.Sys;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

@Mod(modid = "holdmod", version = "1.0", name = "HoldMod")
public class HoldMod {

    public int num1 = 0;
    public int num2 = 0;
    public int num3 = 0;
    public boolean ticking = true;
    private static final Logger LOGGER = LogManager.getLogger();
    Robot robot = new Robot();
    public boolean roboton = false;
    public boolean havent_gun = true;
    public boolean robot_control = false;
    public String wepon_name = "none";
    public String[] guns_DisplayName = {"special", "Bolt Action Rifle", "M4 Assault Rifle"};//判定用変数、仮仕様aa

    public HoldMod() throws AWTException {
        LOGGER.info("Welcome to munecraft mod!!!");
        MinecraftForge.EVENT_BUS.register(this);
    }

    //右クリックを検知
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void right_press(RightClickItem event) {
        if (num1 < 99){
            num1++;
        }
        else {
            num1 = 0;
        }

        if (!robot_control){
            String hand_name = event.getEntityPlayer().getHeldItemMainhand().getDisplayName();
            if(detectHavent_gun(hand_name)){
                setHavent_gun();
            }
            else {
                if(wepon_name.equals(hand_name)){
                    event.setCanceled(true);
                    num3 = 0;
                }
                else{
                    setHave_gun(hand_name);
                }
            }
        }
    }

    //右クリック終了を検知
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void TickEvent(TickEvent.PlayerTickEvent event){

        System.out.print("num1:");
        System.out.println(num1);
        System.out.print("num2:");
        System.out.println(num1);
        System.out.print("num3:");
        System.out.println(num1);
        System.out.print("wepon_name:");
        System.out.println(wepon_name);

        if (!robot_control && ticking){
            ticking = false;
            EntityPlayer player = Minecraft.getMinecraft().player;
            if (player != null){
                String hand_name = player.getHeldItemMainhand().getDisplayName();
                System.out.print("hand_name:");
                System.out.println(hand_name);
                if (num1 == num2){
                    num3++;

                    if (num3 > 50){//release
                        System.out.println("Release");
                        if (wepon_name.equals(hand_name)){
                            robotClick();
                        }
                        setHavent_gun();
                    }

                    else {//press
                        System.out.println("press");
                    }
                }
                else {
                    num2 = num1;
                    num3 = 0;
                }
            }
            ticking = true;
        }
    }

    boolean detectHavent_gun(String hand){
        for (String s : guns_DisplayName) {
            if (hand.equals(s)) {
                return false;
            }
        }
        return true;
    }

    void setHavent_gun(){
        havent_gun = true;
        wepon_name = "none";
    }

    void setHave_gun(String name){
        havent_gun = false;
        wepon_name = name;
    }

    void robotClick(){
        robot_control = true;
        robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        robot_control = false;
    }
}
