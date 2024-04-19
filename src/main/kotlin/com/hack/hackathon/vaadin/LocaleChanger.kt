package com.hack.hackathon.vaadin

import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.html.Div
import java.util.*

class LocaleChanger : Div() {
    init {
        val changeLocaleCombo: ComboBox<Locale> =
            ComboBox<Locale>(getTranslation("app.locale.changer"), LocalesProvider.LOCALES)
        changeLocaleCombo.setValue(locale)
        changeLocaleCombo.setItemLabelGenerator { l: Locale ->
            getTranslation(
                "lang." + l.language.lowercase(Locale.getDefault())
            )
        }
        changeLocaleCombo.addValueChangeListener { event: ComponentValueChangeEvent<ComboBox<Locale?>?, Locale?> ->
            ui.ifPresent { ui: UI ->
                ui.session.locale = event.value
                ui.page.reload()
            }
        }
        add(changeLocaleCombo)
    }
}
