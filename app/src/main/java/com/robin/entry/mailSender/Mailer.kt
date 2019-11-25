package com.robin.entry.mailSender

import android.annotation.SuppressLint
import io.reactivex.Completable
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


object Mailer {

    // NOTE : if you will use google email as admin you need to enable
    //  less secure app from security of the gmail account
    // https://www.google.com/settings/security/lesssecureapps


    @SuppressLint("CheckResult")
    fun sendMail(email: String, subject: String, message: String): Completable {

        return Completable.create { emitter ->

            val props: Properties = Properties().also {
                it.put("mail.smtp.host", "smtp.gmail.com")
                it.put("mail.smtp.socketFactory.port", "465")
                it.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
                it.put("mail.smtp.auth", "true")
                it.put("mail.smtp.port", "465")
            }

            val session = Session.getDefaultInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(Config.EMAIL, Config.PASSWORD)
                }
            })

            try {

                MimeMessage(session).let { mime ->
                    mime.setFrom(InternetAddress(Config.EMAIL))
                    //Adding receiver
                    mime.addRecipient(Message.RecipientType.TO, InternetAddress(email))
                    //Adding subject
                    mime.subject = subject
                    //Adding message
                    mime.setText(message)
                    //send mail
                    Transport.send(mime)
                }

            } catch (e: MessagingException) {
                emitter.onError(e)
            }

            emitter.onComplete()
        }

    }

}