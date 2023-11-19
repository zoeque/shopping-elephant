package zoeque.elephant.usecase.service;

import org.springframework.scheduling.annotation.Scheduled;
import zoeque.elephant.configuration.SettingConfiguration;

public abstract class AbstractShoppingTaskNotifierService
        implements IShoppingTaskHandleService {

  @Override
  @Scheduled(cron = SettingConfiguration.CRON_FOR_NOTIFICATION)
  public void notifyToUser() {
    // TODO find the target items
  }
}
