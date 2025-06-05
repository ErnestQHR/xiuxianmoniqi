package com.xiuxianmoniqi;

import com.xiuxianmoniqi.model.character.Cultivator;
import com.xiuxianmoniqi.model.cultivation.CultivationMethod;
import com.xiuxianmoniqi.model.item.Elixir;
import com.xiuxianmoniqi.model.item.SpiritIncreaseEffect;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║          欢迎来到修仙模拟器！        ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.print("请输入你的道号：");
        String name = scanner.nextLine();
        
        Cultivator cultivator = new Cultivator(name);
        System.out.println("\n" + cultivator.getName() + " 开始了修仙之旅！");
        
        boolean running = true;
        while (running) {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║            修仙模拟器                ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1. 修炼                              ║");
            System.out.println("║ 2. 突破                              ║");
            System.out.println("║ 3. 查看状态                          ║");
            System.out.println("║ 4. 查看背包                          ║");
            System.out.println("║ 5. 炼丹                              ║");
            System.out.println("║ 6. 探索                              ║");
            System.out.println("║ 7. 查看成就                          ║");
            System.out.println("║ 8. 学习心法                          ║");
            System.out.println("║ 9. 存档                              ║");
            System.out.println("║ 0. 读档                              ║");
            System.out.println("║ q. 退出                              ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("请选择操作：");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符
            
            try {
                switch (choice) {
                    case 1:
                        System.out.println("\n开始修炼...");
                        cultivator.cultivate();
                        if (cultivator.isDead()) {
                            running = false;
                            break;
                        }
                        System.out.println("修炼完成！");
                        break;
                    case 2:
                        System.out.println("\n准备突破...");
                        boolean success = cultivator.breakthrough();
                        if (cultivator.isDead()) {
                            running = false;
                            break;
                        }
                        if (success) {
                            System.out.println("突破成功！");
                        } else {
                            System.out.println("突破失败！");
                        }
                        break;
                    case 3:
                        System.out.println(cultivator.getStatusInfo());
                        break;
                    case 4:
                        System.out.println(cultivator.getInventory());
                        break;
                    case 5:
                        System.out.println("\n=== 炼丹房 ===");
                        System.out.println("1. 炼制聚气丹 (消耗100灵石)");
                        System.out.println("2. 炼制筑基丹 (消耗500灵石)");
                        System.out.println("3. 炼制金丹丹 (消耗2000灵石)");
                        System.out.print("请选择要炼制的丹药：");
                        int elixirChoice = scanner.nextInt();
                        Elixir elixir = null;
                        
                        switch (elixirChoice) {
                            case 1:
                                elixir = Elixir.createJujiDan();
                                break;
                            case 2:
                                elixir = Elixir.createZhujiDan();
                                break;
                            case 3:
                                elixir = Elixir.createJindanDan();
                                break;
                            default:
                                System.out.println("无效的选择！");
                                break;
                        }
                        
                        if (elixir != null) {
                            if (cultivator.refineElixir(elixir)) {
                                System.out.println("炼制成功！获得" + elixir.getName());
                            } else {
                                System.out.println("灵石不足，无法炼制！");
                            }
                        }
                        break;
                    case 6:
                        System.out.println("\n开始探索...");
                        try {
                            cultivator.explore();
                            if (cultivator.isDead()) {
                                running = false;
                                break;
                            }
                        } catch (Exception e) {
                            System.out.println("探索失败：" + e.getMessage());
                        }
                        break;
                    case 7:
                        System.out.println("\n=== 成就系统 ===");
                        for (var achievement : cultivator.getAchievements()) {
                            System.out.println("- " + achievement.getName() + ": " + achievement.getDescription());
                        }
                        break;
                    case 8:
                        System.out.println("\n=== 心法学习 ===");
                        System.out.println(cultivator.getAvailableMethods());
                        System.out.print("请输入要学习的心法名称（输入0返回）：");
                        String methodName = scanner.nextLine();
                        if (!methodName.equals("0")) {
                            if (cultivator.learnMethod(methodName)) {
                                System.out.println("学习成功！");
                            } else {
                                System.out.println("学习失败！可能是境界不足或灵石不够。");
                            }
                        }
                        break;
                    case 9:
                        System.out.println("\n保存游戏...");
                        // TODO: 实现存档功能
                        break;
                    case 0:
                        System.out.println("\n读取游戏...");
                        // TODO: 实现读档功能
                        break;
                    case 'q':
                        running = false;
                        System.out.println("\n感谢使用修仙模拟器！");
                        break;
                    default:
                        System.out.println("无效的选择，请重试！");
                }
            } catch (Exception e) {
                System.out.println("发生错误：" + e.getMessage());
            }
        }
        
        scanner.close();
    }
} 