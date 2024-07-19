package com.example.engelsizrehber;

public class Etkinlik {
     String title;
     String content;
     String startDate;
     String finishDate;
     String eventLocal;


     public Etkinlik() {
     }
     public Etkinlik(String title, String content, String startDate,String finishDate,String eventLocal) {
          this.title = title;
          this.content = content;
          this.startDate = startDate;
          this.finishDate = finishDate;
          this.eventLocal = eventLocal;
     }
}
