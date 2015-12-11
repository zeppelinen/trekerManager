package trekermanager;

import UI.Start;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

// класс с пустым конструктором - предусмотрен для наблюдением за статусом Listenerов в коллекции WatcherList в static объекте MainFrom(Start.mf)
// функционал для перезапуска при ручном добавлении нового устройства не доделан
public class Watcher implements Runnable {

    private Map DeviceList = new HashMap<String, Device>();
    private Map timeMap = new HashMap<Device, Long>();
    private Set<String> keys;
    private long time1;
    private long time2;
    private final int timeLimit = 120000;

    public Watcher() {

    }

    private void Check() {

        while (true) {
            keys = Start.mf.getDevicesKeySet();
            DeviceList = Start.mf.getDeviceList(); // заполняем локальный DeviceList элементами родительского
            timeMap = Start.mf.getTimeMap();
            for (String key : keys) {
                Device device = (Device) DeviceList.get(key); // вытаскиваем device из DeviceList по ключу, чтобы затем использовать в качестве ключа в коллекции WatcherList и вернуть статус

                time1 = (Long) timeMap.get(device);
                time2 = System.currentTimeMillis();
                if (Start.mf.getWatcherStatus(device)) { // СЮДА НУЖНО БЫ ДОБАВИТЬ ПРОВЕРКУ "ВКЛЮЧЕННОСТИ" УСТРОЙСТВА
                    if (time2 - time1 > timeLimit && Start.mf.getWatcherStatus(device) == true) {
                        System.err.println("TIMELIMIT OVERLAPTED");
                        Start.mf.setWatcherStatus(device, false);

                    }
                }
                if (Start.mf.getWatcherStatus(device) == false) {
                    Start.mf.createListener(device);// если false - создаём listener с этим устройством
                }
            }

            try {
                Thread.sleep(1000); // чтобы не вешать систему проверка проходит раз в секунду
            } catch (InterruptedException ex) {
                Logger.getLogger(Watcher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void run() {
        Check();
    }
}
