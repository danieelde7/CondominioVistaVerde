package logic;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Lógica para el envío automatizado de correos electrónicos.
 */
public class ServicioCorreo {

    // IMPORTANTE: Para usar Gmail, necesitas generar una "Contraseña de Aplicación" 
    // en la configuración de seguridad de tu cuenta de Google.
// Tu correo real de Gmail
    private static final String REMITENTE = "ecasasolar@miumg.edu.gt"; 
    
    // La contraseña de 16 letras que te acaba de dar Google (sin espacios)
    private static final String PASSWORD = "ywxe qwyx civo jkqu"; 

    public static void enviarComprobante(String destinatario, int numCasa, String mes, int anio, double monto) {
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMITENTE, PASSWORD);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(REMITENTE));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            mensaje.setSubject("Comprobante de Pago - Condominio Vista Verde");
            
            // Cuerpo del correo estructurado
            String contenido = "<h3>Confirmación de Pago Exitoso</h3>"
                    + "<p>Estimado propietario de la Casa #" + numCasa + ",</p>"
                    + "<p>Le confirmamos que hemos recibido su pago de mantenimiento correspondiente a:</p>"
                    + "<ul>"
                    + "<li><b>Mes:</b> " + mes + " " + anio + "</li>"
                    + "<li><b>Monto Cancelado:</b> Q" + String.format("%.2f", monto) + "</li>"
                    + "</ul>"
                    + "<p>Gracias por su puntualidad.</p>"
                    + "<br><p><i>Administración Vista Verde</i></p>";

            mensaje.setContent(contenido, "text/html; charset=utf-8");
            // Encabezados para dar formalidad al correo y mitigar el Spam
            mensaje.setHeader("X-Priority", "1"); // Prioridad Alta
            mensaje.setHeader("X-Mailer", "JavaMail-CondominioVistaVerde");
            
            // Enviar el correo
            Transport.send(mensaje);
            // Enviar el correo
            Transport.send(mensaje);
            System.out.println("Comprobante enviado exitosamente a: " + destinatario);

        } catch (MessagingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
        }
    }
}
