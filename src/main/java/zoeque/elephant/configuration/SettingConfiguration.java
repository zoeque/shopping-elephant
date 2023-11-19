package zoeque.elephant.configuration;

public class SettingConfiguration {
  public static final String CRON_FOR_NOTIFICATION
          = "${zoeque.limitchecker.schedule:0 12 * * * *}";
}
