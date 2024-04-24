import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Runtime.getRuntime;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) throws IOException, InterruptedException {
        spisok_hero.add(village);
        spisok_hero.add(arm);
        spisok_hero.add(mage);

        start_screen();
    }

    //Стартовое меню
    static void start_screen() throws IOException, InterruptedException {
        System.out.println(ANSI_RESET + " -----------------------------------------" + ANSI_RESET);
        System.out.println(ANSI_RED + "             {Dice Dungeon}" + ANSI_RESET);
        System.out.println(ANSI_RESET + " -----------------------------------------" + ANSI_RESET);
        System.out.println();
        System.out.println(ANSI_RESET + "          1. Start new game" + ANSI_RESET);
        System.out.println(ANSI_RESET + "          2. Help" + ANSI_RESET);
        System.out.println();
        System.out.println(ANSI_RESET + " -----------------------------------------" + ANSI_RESET);
        System.out.println("Your aswer:");
        Scanner out = new Scanner(System.in);
        int otv = out.nextInt();
        if (otv != 1 && otv != 2) {
            for(int i=0; i<10; i++) System.out.println("\n\n\n\n\n\n\n\n\n\n");
            start_screen();
            return;
        }
        else{
            if (otv == 1){
                change_hero();
            }
            else {
                System.out.println("Авторы: Такие-то");
            }
        }
    }

    public static void change_hero(){
        for(int i=0; i<10; i++) System.out.println("\n\n\n\n\n\n\n\n\n\n");
        Scanner out = new Scanner(System.in);
        System.out.println(ANSI_RESET + " -----------------------------------------" + ANSI_RESET);
        System.out.println(ANSI_GREEN+"<> Введите своё имя: "+ANSI_RESET);
        String your_name = out.nextLine();
        System.out.println(ANSI_GREEN+"<> Выберите класс: "+ANSI_RESET);
        
        for (int i = 0; i<=spisok_hero.size()-1; i++){
            System.out.println(ANSI_GREEN+"<> "+ String.valueOf(i+1) +" - "+spisok_hero.get(i).name);
        }
        int type_class = out.nextInt();
        System.out.println(ANSI_GREEN+"<> Вы выбрали класс: "+spisok_hero.get(type_class-1).name +ANSI_RESET);
        hp_player = spisok_hero.get(type_class-1).hp;
        type_charactor = spisok_hero.get(type_class-1).type_character;

        System.out.println(ANSI_RESET + " -----------------------------------------" + ANSI_RESET);
        start_random_lock();
    }
    static String[] location = {"Станице","Лесу","Деревне"};
    static String[] items_quest = {"кольцо","меч героя","семейная лопата"};
    static void start_random_lock(){
        Random dice = new Random();
        int chislo = dice.nextInt(0,location.length-1);
        load_hud_up();
        System.out.println(ANSI_WHITE+"Вы открыли глаза в "+location[chislo]+".\nГолова болела, а вы пытались вспомнить\nчто было вчера.");
        loca = chislo;
        chislo = dice.nextInt(0,items_quest.length-1);
        item_search = chislo;
        String text_quest = "";
        switch (chislo){
            case 0:
                text_quest = ANSI_WHITE+"Вы вспомнили, что потеряли "+ANSI_RESET+ANSI_YELLOW+"кольцо"+ANSI_RESET+ANSI_WHITE+",\nбоюсь ваш брат будет зол, ведь его свадьба сорвётся.";
                break;
            case 1:
                text_quest = ANSI_WHITE+"Вы вспомнили, что потеряли "+ANSI_RESET+ANSI_YELLOW+"меч героя"+ANSI_RESET+ANSI_WHITE+",\nкогда герой об этом узнает.... лучше и не знать что будет";
                break;
            case 2:
                text_quest = ANSI_WHITE+"Вы вспомнили, что потеряли "+ANSI_RESET+ANSI_YELLOW+"семейную лопату"+ANSI_RESET+ANSI_WHITE+",\nващ папа будет недоволен.";
                break;
        }
        quest = "Найди "+items_quest[chislo];
        System.out.println(text_quest);
        load_hud_down();
        input_otvet();
    }
    static int stage = 0;
    static void input_otvet(){
        Scanner out = new Scanner(System.in);
        int itv = out.nextInt();

        if (stage == 0){
            if (itv == 1){
                //Осмотреться
                see_lock();
            }
            else{
                //Идти в
                stage += 1;
                travel();
            }
        }
        else if (stage == 1){
            stage -= 1;
            if (itv == 1){
                if (loca == 1)
                    get_message("Ближайшего магазина в лесу не оказалось...");
                else
                    get_message("Ближайший магазин был, только вот денег при вас тоже не было...");
            }
            else if (itv == 2){
                if (loca == 1){
                    hp_player -= 1;
                    get_message("Ближайшего поселения в лесу не было найдено...");

                }
                else
                    get_message("Нужно '"+quest+"' именно там где вы и есть.");
            }
            else{
                hp_player = -1;
                if (loca == 1)
                    get_message("Вы проиграли. Вас съели волки.");
                else
                    get_message("Вы проиграли. Погибли от голода.");

            }

        }

    }
    static void get_message(String message){
        load_hud_up();
        System.out.println(ANSI_WHITE+message);

        if (hp_player > 0){
            load_hud_down();
            input_otvet();
        }
    }

    static void travel(){
        load_hud_up();
        System.out.println(ANSI_WHITE+"1: Ближайший магазин.");
        System.out.println(ANSI_WHITE+"2: Какое-то более интересное поселение.");
        System.out.println(ANSI_WHITE+"3: Сесть там где стою и ничего не делать.");
        load_hud_down();
        input_otvet();
    }

    static void see_lock(){
        load_hud_up();
        switch (loca){
            case 0:
                System.out.println(ANSI_WHITE+"Обычная Станица. Много домов, магазинов и прочего.");
                break;
            case 1:
                System.out.println(ANSI_WHITE+"Густой лес. Не факт что вы оттуда выберетесь.");
                break;
            case 2:
                System.out.println(ANSI_WHITE+"Обычная маленькая деревушка.");
                break;
        }
        load_hud_down();
        input_otvet();
    }

    static int loca = 0;
    static int item_search = 0;
    static String quest = "";
    static int hp_player = -1;
    static int type_charactor = -1;

    static void load_hud_up(){
        for(int i=0; i<10; i++) System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.println(ANSI_RED + "-----------------------------------------" + ANSI_RESET);
        System.out.println(ANSI_RED + "<> HP: "+hp_player + ANSI_RESET);
        System.out.println(ANSI_RED + "-----------------------------------------" + ANSI_RESET);
    }



    static void load_hud_down(){
        System.out.println(ANSI_RED + "-----------------------------------------" + ANSI_RESET);
        System.out.println(ANSI_RED + " 1- Осмотреться; 2- Идти в...");
        System.out.println(ANSI_RED + "-----------------------------------------" + ANSI_RESET);
    }
    ////////////////////////////////////////////////////

    static character village = new character("Селянин",3,-1,0);
    static character arm = new character("Рыцарь",4,1,1);
    static character mage = new character("Маг",2,5,2);

    static List<character> spisok_hero = new ArrayList<character>();

    static class character{
        String name = "";
        int hp = 0;
        int manna = -1;
        int type_character = 0;
        /*
         0 - Селянин
         1 - Рыцарь
         2 - Маг
         */

        character(String _name, int _hp, int _manna, int _type){
            this.name = _name;
            this.hp = _hp;
            this.manna = _manna;
            this.type_character = _type;
        }
    }
}