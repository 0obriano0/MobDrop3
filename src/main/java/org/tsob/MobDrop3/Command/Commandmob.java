package org.tsob.MobDrop3.Command;

import java.util.ArrayList;
import java.util.Arrays;

public class Commandmob extends mainCommandSystem{
  public Commandmob() {
    super(  "mob",
        "/mobdrop mob 怪物列表設定",
        new ArrayList<String>(Arrays.asList("mobdrop.admin.mob")),
        "/mob");
  }
}
