import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class AmountNotAllowedException extends Exception {
    AmountNotAllowedException(int m) {
        super("Receipt with amount " +m+ " is not allowed to be scanned");
    }
}
class Smetka {
    private String id;
    Map<String, List<Integer>> ceni;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public float getTaxReturn() {
        float taxReturn = 0;

        taxReturn += ((float)ceni.get("A").stream().mapToInt(i->i).sum()*0.15 * 0.18);

        taxReturn += ((float)ceni.get("B").stream().mapToInt(i->i).sum()*0.15 * 0.05);

        return taxReturn;
    }

    public int getTotalAmount() {
        return ceni.values().stream().mapToInt(i-> i.stream().mapToInt(k->k).sum()).sum();
    }

    public Smetka(String id, Map<String, List<Integer>> ceni) throws AmountNotAllowedException{

        int suma = ceni.values().stream().mapToInt(i-> i.stream().mapToInt(k->k).sum()).sum();

        if(suma>30000) {
            throw new AmountNotAllowedException(suma);
        }
        this.id = id;
        this.ceni = ceni;
    }
}


class MojDDV {
    List<Smetka> smetki;

    public MojDDV() {
        this.smetki = new ArrayList<>();
    }

    public void readRecords (InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while(true) {

            String line = br.readLine();
            if(line ==  null) {
                break;
            }
            String[] parts = line.split("\\s+");


            Map<String, List<Integer>> mapa = new HashMap<>();

            mapa.computeIfAbsent("A", k-> new ArrayList<>());
            mapa.computeIfAbsent("B", k-> new ArrayList<>());
            mapa.computeIfAbsent("V", k-> new ArrayList<>());

            for (int i = 1; i < parts.length; i += 2) {
                mapa.get(parts[i+1]).add(Integer.parseInt(parts[i]));
            }

            try {
                smetki.add(new Smetka(parts[0], mapa));
            } catch (AmountNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public void printTaxReturns(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);

        for(int i=0; i<smetki.size(); i++) {
            System.out.println(smetki.get(i).getId()
                    + " " + smetki.get(i).getTotalAmount()
                    + " " + String.format("%.2f",smetki.get(i).getTaxReturn()));
        }

        pw.flush();
    }
}



public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        try {
            mojDDV.readRecords(System.in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

    }
}