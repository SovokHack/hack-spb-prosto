package com.hack.hackathon.vaadin

import com.vaadin.flow.i18n.I18NProvider
import lombok.RequiredArgsConstructor
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.*

@Component
class LocalesProvider(
    private val messageSource: MessageSource
) : I18NProvider {


    override fun getProvidedLocales(): List<Locale> {
        return LOCALES
    }

    override fun getTranslation(key: String, locale: Locale, vararg params: Any): String {
        return messageSource.getMessage(key, params, locale)
    }

    companion object {
        val EN: Locale = Locale.US
        val RU: Locale = Locale("ru", "RU")
        val LOCALES: List<Locale> = java.util.List.of(EN, RU)
    }
}
