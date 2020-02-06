import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


class Subtitle {
    private int redenBroj;
    private String vreme;
    private String tekst;

    public Subtitle(int redenBroj, String vreme, String tekst) {
        this.redenBroj = redenBroj;
        this.vreme = vreme;
        this.tekst = tekst;
    }

    public int getRedenBroj() {
        return redenBroj;
    }

    public void setRedenBroj(int redenBroj) {
        this.redenBroj = redenBroj;
    }

    public String getVreme() {
        return vreme;
    }

    public void setVreme(String vreme) {
        this.vreme = vreme;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String toString() {
        return this.redenBroj+"\n"+this.vreme+"\n"+this.tekst;
    }

}



class Subtitles {
    private List<Subtitle> list;
    public Subtitles() {
        this.list = new ArrayList<>();
    }

    public int loadSubtitles(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        int broj = 0;
        while(true) {
            String redenBroj2 = br.readLine();
            if(redenBroj2==null) {
                break;
            }
            int redenBroj = Integer.parseInt(redenBroj2);
            String vreme = br.readLine();
            String tekst2;
            StringBuilder sb = new StringBuilder();
            while(true) {

                String tekst = br.readLine();
                if(tekst == null) {
                    break;
                }
                if(tekst.equals("")) {
                    break;
                }
                sb.append(tekst+"\n");
            }

            tekst2 = sb.toString();

            this.list.add(new Subtitle(redenBroj, vreme, tekst2));
            broj++;

        }
        return broj;
    }

    public void print() {
        list.stream().forEach(System.out::println);
    }

    public void shift(int ms) {
        for(int i=0; i<list.size(); i++) {
            Subtitle subtitle = list.get(i);
            String vreme = subtitle.getVreme();
            String startTime = vreme.split(" --> ")[0];
            String endTime = vreme.split(" --> ")[1];

            int startHours = Integer.parseInt(startTime.split(":")[0]);
            int startMinutes = Integer.parseInt(startTime.split(":")[1]);
            int startSeconds = Integer.parseInt(startTime.split(":")[2].split(",")[0]);
            int startMiliseconds = Integer.parseInt(startTime.split(",")[1]);

            int startMiliFull = startHours*3600000+startMinutes*60000+startSeconds*1000+startMiliseconds;
            startMiliFull+=ms;


            int endHours = Integer.parseInt(endTime.split(":")[0]);
            int endMinutes = Integer.parseInt(endTime.split(":")[1]);
            int endSeconds = Integer.parseInt(endTime.split(":")[2].split(",")[0]);
            int endMiliseconds = Integer.parseInt(endTime.split(",")[1]);

            int endMiliFull = endHours*3600000+endMinutes*60000+endSeconds*1000+endMiliseconds;
            endMiliFull+=ms;

            String newStartTime = String.format("%02d:%02d:%02d,%03d", startMiliFull/3600000,
                    startMiliFull%3600000/60000,startMiliFull%60000/1000,
                    startMiliFull%1000);

            String newEndTime = String.format("%02d:%02d:%02d,%03d", endMiliFull/3600000,
                    endMiliFull%360000/60000,endMiliFull%60000/1000,
                    endMiliFull%1000);

            String newTime = newStartTime+" --> " + newEndTime;

            subtitle.setVreme(newTime);



        }
    }


}

public class SubtitlesTest {
    public static void main(String[] args)  {
        Subtitles subtitles = new Subtitles();
        int n = 0;
        try {
            n = subtitles.loadSubtitles(System.in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("+++++ ORIGINIAL SUBTITLES +++++");
        subtitles.print();
        int shift = n * 37;
        shift = (shift % 2 == 1) ? -shift : shift;
        System.out.println(String.format("SHIFT FOR %d ms", shift));
        subtitles.shift(shift);
        System.out.println("+++++ SHIFTED SUBTITLES +++++");
        subtitles.print();
    }
}

// Вашиот код овде
