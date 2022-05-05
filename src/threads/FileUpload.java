package threads;

import model.data_structures.AVL;
import model.data_structures.BST;
import model.data_structures.MeLinkedLists;
import model.data_structures.RBT;
import model.objects.Country;
import model.objects.Crud;
import model.objects.Person;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class FileUpload extends Thread{

    private BufferedReader br1;
    private BufferedReader br2;
    private Crud fb;
    private int amount;
    private static MeLinkedLists<Country> countries;

    public FileUpload(BufferedReader br1, BufferedReader br2, Crud fb, int amount) {
       this.br1 = br1;
       this.br2 = br2;
       this.fb = fb;
       this.amount = amount;
    }

    @Override
    public void run() {
        override();

        try {
            readCountry();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int num = 0;
        int num2 = 0;
        int limit = (int) Math.ceil(amount/2);
        int limit2 = (int) Math.ceil(amount/limit);
        String line1 = null;
        String line2 = "null";

        try {
            line1 = br1.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (line1 != null && num < limit) {
            try {

                line1 = br1.readLine();

                if (line1 != null) {
                    String name = line1.split(",")[0];

                    while (line2 != null  && num2 < limit2){
                        line2 = br2.readLine();

                        if (line2 != null) {
                            String lastName = line2.split(",")[0];
                            String sex = (Math.random() < 0.5) ? "Women" : "Men";
                            String birtday = getRandomDate();
                            String height = new DecimalFormat("#.00").format((Math.random() * (1.80 - 1.50) + 1.50));
                            String nationality = getNationality();

                            Person p = new Person(fb.getTotalPeople() + 1, name, lastName, sex, birtday, height, nationality);
                            fb.addPerson(p);

                            num2++;

                        }

                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            num++;
            num2 = 0;


        }

        try {
            br1.close();
            br2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void override(){
        fb.setTotalPeople(0);
        fb.setRBTPersonFullName(new RBT<>());
        fb.setBSTPersonLastName(new BST<>());
        fb.setRBTPersonName(new RBT<>());
        fb.setAVLPersonCode(new AVL<>());

    }

    public  int[] distribution(){
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


    public String getRandomDate() {
        int[] limit = distribution();
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), ((int) (Math.random()*(13-1)+1)), ((int) (Math.random()*(31-1)+1)));

        return new SimpleDateFormat("dd").format(cal.getTime()) + "/" + new SimpleDateFormat("MM").format(cal.getTime()) + "/" + ((int) (Math.random()*(limit[1]-limit[0])+limit[0]));
    }

    public static String getNationality() {
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
