package zoeque.elephant.usecase.service.mailer;

import io.vavr.control.Try;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import zoeque.elephant.configuration.MailServiceCollector;
import zoeque.elephant.domain.model.MailService;
import zoeque.elephant.domain.model.MailServiceProviderModel;

/**
 * The class to send the mail via {@link JavaMailSender}.
 */
@Service
@EnableAsync
@MailService(MailServiceProviderModel.OTHERS)
public class MailSenderService extends AbstractMailSenderService {
  JavaMailSender javaMailSender;

  public MailSenderService(@Value("${zoeque.elephant.mail.address.to:null}")
                           String toMailAddress,
                           @Value("${zoeque.elephant.mail.address.from:null}")
                           String fromMailAddress,
                           @Value("${zoeque.elephant.mail.provider:GMAIL}")
                           MailServiceProviderModel model,
                           MailServiceCollector collector,
                           JavaMailSender javaMailSender) {
    super(toMailAddress, fromMailAddress, model, collector);
    this.javaMailSender = javaMailSender;
  }

  /**
   * Send e-mail with full parameter in application.properties
   */
  @Async
  public Try<String> sendMailToUser(String subject,
                                    String messageContent) {
    MimeMessage message = javaMailSender.createMimeMessage();
    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom(fromMailAddress);
      helper.setTo(toMailAddress);
      helper.setSubject(subject);
      helper.setText(messageContent);
      javaMailSender.send(message);
      return Try.success(messageContent);
    } catch (Exception e) {
      return Try.failure(e);
    }
  }
}
