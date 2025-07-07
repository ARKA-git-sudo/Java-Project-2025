package com.example.RailMitra.controller;

import com.example.RailMitra.entity.Station;
import com.example.RailMitra.entity.Train;
import com.example.RailMitra.entity.TrainSchedule;
import com.example.RailMitra.repo.StationRepo;
import com.example.RailMitra.repo.TrainRepo;
import com.example.RailMitra.repo.TrainScheduleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class Test {
    @Autowired
    StationRepo stationRepo;

    @Autowired
    TrainRepo trainRepo;

    @Autowired
    TrainScheduleRepo trainScheduleRepo;

    @GetMapping
    public void test(){
        Station delhi = new Station(null, "New Delhi","NDLS");
        Station mumbai = new Station(null, "Mumbai Central","CSMT");
        Station howrah = new Station(null, "Howrah Junction","HWR");
        Station kanpur = new Station(null, "Kanpur Central","CNB");
        Station chennai = new Station(null, "Chennai Central", "MAS");
        Station secunderabad = new Station(null, "Secunderabad Junction", "SC");
        Station ahmedabad = new Station(null, "Ahmedabad Junction", "ADI");
        Station patna = new Station(null, "Patna Junction", "PNBE");
        Station lucknow = new Station(null, "Lucknow NR", "LKO");
        Station bangalore = new Station(null, "Bangalore City Junction", "SBC");
        Station kolkata = new Station(null, "Kolkata", "KOAA");
        Station jaipur = new Station(null, "Jaipur Junction", "JP");
        Station bhopal = new Station(null, "Bhopal Junction", "BPL");
        Station nagpur = new Station(null, "Nagpur", "NGP");
        Station varanasi = new Station(null, "Varanasi Junction", "BSB");
        Station guwahati = new Station(null, "Guwahati", "GHY");
        Station trivandrum = new Station(null, "Trivandrum Central", "TVC");
        Station vizag = new Station(null, "Visakhapatnam", "VSKP");
        Station coimbatore = new Station(null, "Coimbatore Junction", "CBE");
        Station mangalore = new Station(null, "Mangalore Junction", "MAJN");
        Station chandigarh = new Station(null, "Chandigarh", "CDG");
        Station vijayawada = new Station(null, "Vijayawada Junction", "BZA");
        Station pune = new Station(null, "Pune Junction", "PUNE");
        Station muzaffarpur = new Station(null, "Muzaffarpur Junction", "MFP");
        Station amritsar = new Station(null, "Amritsar Junction", "ASR");

        stationRepo.saveAll(List.of(delhi,mumbai,howrah,kanpur,coimbatore,chennai,secunderabad,ahmedabad,patna,lucknow,
                bangalore,kolkata,jaipur,bhopal,nagpur,varanasi,guwahati,trivandrum,vizag,mangalore,chandigarh,pune,vijayawada,muzaffarpur,amritsar));

        Train t1 = new Train(null, "Rajdhani Express", "12301", null);
        Train t2 = new Train(null, "Shatabdi Express", "12001", null);
        Train t3 = new Train(null, "Duronto Express", "12269", null);
        Train t4 = new Train(null, "Garib Rath Express", "12909", null);
        Train t5 = new Train(null, "Sampark Kranti Express", "12649", null);
        Train t6 = new Train(null, "Vande Bharat Express", "22436", null);
        Train t7 = new Train(null, "Tejas Express", "22671", null);
        Train t8 = new Train(null, "Humsafar Express", "12571", null);
        Train t9 = new Train(null, "Double Decker Express", "12931", null);
        Train t10 = new Train(null, "Intercity Express", "12111", null);
        Train t11 = new Train(null, "Jan Shatabdi Express", "12071", null);
        Train t12 = new Train(null, "Antyodaya Express", "22877", null);
        Train t13 = new Train(null, "Superfast Express", "12393", null);
        Train t14 = new Train(null, "Uday Express", "22701", null);
        Train t15 = new Train(null, "Yuva Express", "12249", null);
        Train t16 = new Train(null, "Mahamana Express", "22417", null);
        Train t17 = new Train(null, "Suhaildev Express", "22433", null);
        Train t18 = new Train(null, "Bharat Gaurav Express", "00611", null);
        Train t19 = new Train(null, "Amrit Bharat Express", "15888", null);
        Train t20 = new Train(null, "Jansadharan Express", "15211", null);


        trainRepo.saveAll(List.of(t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20));


        TrainSchedule s1 = new TrainSchedule(null, t1, delhi, mumbai, "16:55", "08:35");
        TrainSchedule s2 = new TrainSchedule(null, t2, delhi, lucknow, "06:10", "12:30");
        TrainSchedule s3 = new TrainSchedule(null, t3, mumbai, howrah, "20:15", "13:05");
        TrainSchedule s4 = new TrainSchedule(null, t4, chennai, secunderabad, "16:40", "06:30");
        TrainSchedule s5 = new TrainSchedule(null, t5, patna, delhi, "14:00", "05:50");
        TrainSchedule s6 = new TrainSchedule(null, t6, delhi, varanasi, "06:00", "14:00");
        TrainSchedule s7 = new TrainSchedule(null, t7, mumbai, ahmedabad, "15:45", "21:55");
        TrainSchedule s8 = new TrainSchedule(null, t8, bangalore, patna, "13:30", "19:45");
        TrainSchedule s9 = new TrainSchedule(null, t9, ahmedabad, jaipur, "06:00", "12:40");
        TrainSchedule s10 = new TrainSchedule(null, t10, lucknow, kanpur, "09:30", "10:45");
        TrainSchedule s11 = new TrainSchedule(null, t11, howrah, bhopal, "07:15", "18:55");
        TrainSchedule s12 = new TrainSchedule(null, t12, trivandrum, chennai, "22:00", "12:30");
        TrainSchedule s13 = new TrainSchedule(null, t13, delhi, chandigarh, "18:20", "22:30");
        TrainSchedule s14 = new TrainSchedule(null, t14, vizag, vijayawada, "05:30", "10:10");
        TrainSchedule s15 = new TrainSchedule(null, t15, delhi, lucknow, "16:30", "22:00");
        TrainSchedule s16 = new TrainSchedule(null, t16, varanasi, delhi, "06:15", "14:00");
        TrainSchedule s17 = new TrainSchedule(null, t17, guwahati, howrah, "10:00", "22:30");
        TrainSchedule s18 = new TrainSchedule(null, t18, pune, coimbatore, "08:00", "23:00");
        TrainSchedule s19 = new TrainSchedule(null, t19, guwahati, kolkata, "12:40", "21:00");
        TrainSchedule s20 = new TrainSchedule(null, t20, muzaffarpur, amritsar, "14:00", "11:10");
        TrainSchedule s21 = new TrainSchedule(null, t1, howrah, delhi, "17:00", "10:05");
        TrainSchedule s22 = new TrainSchedule(null, t2, lucknow, delhi, "15:15", "21:25");
        TrainSchedule s23 = new TrainSchedule(null, t3, howrah, mumbai, "19:00", "11:30");
        TrainSchedule s24 = new TrainSchedule(null, t4, secunderabad, chennai, "18:50", "08:20");
        TrainSchedule s25 = new TrainSchedule(null, t5, delhi, patna, "18:00", "08:40");
        TrainSchedule s26 = new TrainSchedule(null, t6, varanasi, delhi, "15:00", "23:00");
        TrainSchedule s27 = new TrainSchedule(null, t7, ahmedabad, mumbai, "06:10", "12:30");
        TrainSchedule s28 = new TrainSchedule(null, t8, patna, bangalore, "22:00", "06:30");
        TrainSchedule s29 = new TrainSchedule(null, t9, jaipur, ahmedabad, "14:00", "20:50");
        TrainSchedule s30 = new TrainSchedule(null, t10, kanpur, lucknow, "11:30", "12:45");
        TrainSchedule s31 = new TrainSchedule(null, t11, bhopal, howrah, "06:50", "18:45");
        TrainSchedule s32 = new TrainSchedule(null, t12, chennai, trivandrum, "16:00", "05:30");
        TrainSchedule s33 = new TrainSchedule(null, t13, chandigarh, delhi, "06:20", "10:15");
        TrainSchedule s34 = new TrainSchedule(null, t14, vijayawada, vizag, "16:20", "21:55");
        TrainSchedule s35 = new TrainSchedule(null, t15, lucknow, delhi, "06:40", "12:20");
        TrainSchedule s36 = new TrainSchedule(null, t16, delhi, varanasi, "16:00", "23:45");
        TrainSchedule s37 = new TrainSchedule(null, t17, howrah, guwahati, "05:00", "17:30");
        TrainSchedule s38 = new TrainSchedule(null, t18, coimbatore, pune, "15:00", "05:00");
        TrainSchedule s39 = new TrainSchedule(null, t19, kolkata, guwahati, "08:00", "18:30");
        TrainSchedule s40 = new TrainSchedule(null, t20, amritsar, muzaffarpur, "15:30", "10:20");


        trainScheduleRepo.saveAll(List.of(s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11,s12,s13,s14,s15,s16,s17,s18,s19,s20,s21,s22,s23,s24,s25,s26,s27,s28,s29,s30,s31,s32,s33,s34,s35,s36,s37,s38,s39,s40));

        System.out.println("Data Inserted to database....");
    }
}
