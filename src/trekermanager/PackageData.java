package trekermanager;

import UI.Start;
import java.util.HashMap;

// класс расширяющий абстрактный Pack до пакета с данными. Сделано на всякий случай - если будут ещё другие пакеты и их тоже нужно будет обрабатывать аналогичными способами
// так же, при инициализации через конструктор, происходит запись пакета в базу данных при помощи переопределённого метода
public class PackageData extends Pack {

    String id;
    String date;
    String time;
    String lat = "";
    String lon = "";
    int speed;
    int course;
    int height;
    int sats;
    int idx;
    int mcc;
    int mnc;
    int lac;
    int cid;
    int vext;
    float hdop;
    int digitinput;
    int digitouput;
    String ads;
    String ibutton;
    String params;
    boolean input1;
    boolean input2;
//D#020100;030326;NA;NA;NA;NA;NA;NA;NA;NA;NA;NA;NA;;000000000000;IDX:1:107,MCC:1:250,MNC:1:1,LAC:1:407,CID:1:56625,Vext:1:8376,IN1:1:0,IN2:1:0

    public PackageData(String id, String date, String time, String lat, String lon, int speed, int course, int height, int sats, float hdop, int digitinput, int digitoutput, String ads, String ibutton, String params) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.lat = lat;
        this.lon = lon;
        this.speed = speed;
        this.course = course;
        this.sats = sats;
        this.hdop = hdop;
        this.digitinput = digitinput;
        this.digitouput = digitoutput;
        this.ads = ads;
        this.ibutton = ibutton;
        this.params = params;
        String paramsbody[] = params.split(",");
        HashMap<String, String> parameters = new HashMap<>();
        for (String paramsbody1 : paramsbody) {
            String[] value = paramsbody1.split(":");
            if (paramsbody1.length() > 2) {
                parameters.put(value[0], value[2]);
            }
        }

        for (String key : parameters.keySet()) {
            switch (key) {

                case "IDX":
                    this.idx = Integer.parseInt(parameters.get(key));
                    break;
                case "MCC":
                    this.mcc = Integer.parseInt(parameters.get(key));
                    break;
                case "MNC":
                    this.mnc = Integer.parseInt(parameters.get(key));
                    break;
                case "LAC":
                    this.lac = Integer.parseInt(parameters.get(key));
                    break;
                case "CID":
                    this.cid = Integer.parseInt(parameters.get(key));
                    break;
                case "Vext":
                    this.vext = Integer.parseInt(parameters.get(key));
                    break;
                case "IN1":
                    this.input1 = Integer.parseInt(parameters.get(key)) == 1;
                    break;
                case "IN2":
                    this.input2 = Integer.parseInt(parameters.get(key)) == 1;
                    break;

            }

        }
//        this.idx = Integer.parseInt(IDX[2]);
//        this.mcc = Integer.parseInt(MCC[2]);
//        this.mnc = Integer.parseInt(MNC[2]);
//        this.lac = Integer.parseInt(LAC[2]);
//        this.cid = Integer.parseInt(CID[2]);
//        this.vext = Integer.parseInt(Vext[2]);
//        this.input1 = Integer.parseInt(In1[2]) == 1;
//        this.input1 = Integer.parseInt(In1[2]) == 1;
//
//        String IDX[] = paramsbody[0].split(":");
//        this.idx = Integer.parseInt(IDX[2]);
//
//        String MCC[] = paramsbody[1].split(":");
//        this.mcc = Integer.parseInt(MCC[2]);
//
//        String MNC[] = paramsbody[2].split(":");
//        this.mnc = Integer.parseInt(MNC[2]);
//
//        String LAC[] = paramsbody[3].split(":");
//        this.lac = Integer.parseInt(LAC[2]);
//
//        String CID[] = paramsbody[4].split(":");
//        this.cid = Integer.parseInt(CID[2]);
//
//        String Vext[] = paramsbody[5].split(":");
//        this.vext = Integer.parseInt(Vext[2]);
//
//        String In1[] = paramsbody[6].split(":");
//        this.input1 = Integer.parseInt(In1[2]) == 1;
//
//        String In2[] = paramsbody[7].split(":");
//        this.input2 = Integer.parseInt(In2[2]) == 1;

        System.out.println("Integer.parseInt(In2[2]) ==" + this.input2);
        System.out.println("this.idx="+this.idx+ " this.mcc="+this.mcc+ " this.mnc"+this.mnc+" this.lac="+this.lac+" this.cid="+this.cid+" this.vext="+this.vext+" this.input1="+this.input1+" this.input2="+this.input1);
        SaveToDB();
    }

    @Override
    public void Print() {
        System.out.println("Параметры:" + this.params);
        System.out.println("Состояние устройства:" + this.input1);
    }
// input2 - boolean отображение дискретного сигнала(0,1) с устройства.

    @Override
    public void SaveToDB() {
        System.out.println("PackageData: SaveToDB executed");
        if (this.input2) {
            Start.mf.deviceStatus(this.id, true);
        } else {
            Start.mf.deviceStatus(this.id, false);
        }
        Start.mf.sdb.setPackageData(this);
    }
}
