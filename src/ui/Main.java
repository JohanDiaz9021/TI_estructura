package ui;

import model.data_structures.MeLinkedLists;
import model.objects.Country;
import model.objects.Crud;
import model.objects.Person;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static final String SAVE_PATH_FILE = "data/persistent/Data.txt";
    private static MeLinkedLists<Country> countries;

    public static void main(String[] args) throws Exception {
        driver1();
    }

    public static void driver1() throws Exception {

        Crud fb = new Crud();
        BufferedReader br1 = new BufferedReader(new FileReader("data/test/Names_2010Census.csv"));
        BufferedReader br2 = new BufferedReader(new FileReader("data/test/babynames-clean.csv"));

        init(br1, br2, fb);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
        oos.writeObject(fb);
        oos.close();

    }

    public static void init(BufferedReader br1, BufferedReader br2, Crud fb) throws IOException {

        try {
            readCountry();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int num = 1;
        int num2 = 1;
        DecimalFormat df = new DecimalFormat("#.00");
        String line1 = br1.readLine();


        while (line1 != null && num < 20) {
            try {

                line1 = br1.readLine();

                if (line1 != null) {
                    String name = line1.split(",")[0];

                    String line2 = br2.readLine();

                    while (line2 != null  && num2 < 100){
                        line2 = br2.readLine();

                        String lastName = line2.split(",")[0];
                        String sex = (Math.random() < 0.5)? "Women": "Men";
                        String birtday = getRandomDate();
                        String height =  df.format((Math.random()*(1.80-1.50)+1.50));
                        String nationality = getNationality();

                        Person p = new Person(fb.getTotalPeople() + 1, name, lastName, sex, birtday, height, nationality);

                        fb.addPerson(p);
                        num2++;

                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
                num++;
                num2 = 1;
        }

        try {
            br1.close();
            br2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static int[] distribution(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        double random = Math.random();
        int initYear = 0;
        int endYear = 0;

        if (random >= 0 && random < 0.19){
            initYear = year - 14;
            endYear = year;

        }else if (random >= 0.19 && random < 0.32){
            initYear = year - 24;
            endYear = year - 15;

        }else if(random >= 0.32 && random < 0.72){
            initYear = year - 54;
            endYear = year - 25;

        }else if(random >= 0.72 && random < 0.85){
            initYear = year - 64;
            endYear = year - 55;

        }else{
            initYear = 1900;
            endYear = year - 65;

        }

        return new int[]{initYear, endYear};
    }



    public static String getRandomDate() {
        int[] limit = distribution();
        System.out.println(limit);
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), ((int) (Math.random()*(13-1)+1)), ((int) (Math.random()*(31-1)+1)));

        return new SimpleDateFormat("dd").format(cal.getTime()) + "/" + new SimpleDateFormat("MM").format(cal.getTime()) + "/" + ((int) (Math.random()*(limit[1]-limit[0])+limit[0]));
    }

    public static String getNationality(){
        double random = Math.random();
        boolean flag = true;
        int cont = 0;

        while (flag){
            if (random >= countries.get(cont).getInit() && random < countries.get(cont).getEnd()){
                flag = false;
                return countries.get(cont).getName();
            }

            cont++;
        }

        return null;
    }

    public static void readCountry() throws IOException {
        BufferedReader bn = new BufferedReader(new FileReader("data/test/population_by_country_2020.csv"));
        countries = new MeLinkedLists<>();
        String line = bn.readLine();
        double init = 0;

        while (line != null) {
            line = bn.readLine();

            if (line != null) {

                String[] data = line.split(",");
                String name = data[0];
                double end;

                end = ((Double.parseDouble(data[1])/(790*1000000))/10);

                if (countries.isEmpty()) {
                    countries.add(new Country(name, init, end));

                }else{
                    init = (countries.get(countries.size()-1).getEnd());
                    end = end + init + 0.0000496;
                    countries.add(new Country(name, init, end));
                }

            }else {
                break;
            }

        }


        bn.close();



    }



}
