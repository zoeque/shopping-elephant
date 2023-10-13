package zoeque.elephant.usecase.service;

import io.micrometer.common.util.StringUtils;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import zoeque.elephant.configuration.MailServiceCollector;
import zoeque.elephant.domain.model.MailServiceProviderModel;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;
import zoeque.elephant.usecase.event.MailNotificationEvent;


/**
 * Class to send e-mail
 */
@Slf4j
public abstract class AbstractMailSenderService implements IMailService {
  protected String toMailAddress;
  protected String fromMailAddress;
  protected MailServiceProviderModel model;
  MailServiceCollector collector;

  public AbstractMailSenderService(@Value("${zoeque.elephant.mail.address.to:null}")
                                   String toMailAddress,
                                   @Value("${zoeque.elephant.mail.address.from:null}")
                                   String fromMailAddress,
                                   @Value("${zoeque.elephant.mail.provider:GMAIL}")
                                   MailServiceProviderModel model,
                                   MailServiceCollector collector) {
    this.toMailAddress = toMailAddress;
    this.fromMailAddress = fromMailAddress;
    this.model = model;
    this.collector = collector;
  }

  /**
   * The event listener for {@link MailNotificationEvent}.
   *
   * @param event with a list of {@link ShoppingTaskDto}.
   */
  @EventListener
  public void sendMail(MailNotificationEvent event) {
    if (StringUtils.isEmpty(toMailAddress) || StringUtils.isEmpty(fromMailAddress)) {
      log.error("The mail address must not be null!! Failed to send email!!");
      return;
    }
    collector.findMailService(model)
            .get()
            .sendMailToUser(buildSubject(event), buildMessage(event));
  }

  protected String buildMessage(MailNotificationEvent event) {
    AtomicReference<StringBuilder> reference = new AtomicReference<>();
    reference.get().append("買い物通知アプリケーションからのメールです。\r");
    reference.get().append("以下が購入リマインドとなります。\r");

    event.getTaskList().forEach(taskDto -> {
      reference.get().append(taskDto.itemName());
      reference.get().append("\r");
    });
    return reference.get().toString();
  }

  protected String buildSubject(MailNotificationEvent event) {
         return "【買い物リスト】本日購入予定のリストです。";
  }
}
