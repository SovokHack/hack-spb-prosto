package com.hack.hackathon.layout

import com.hack.hackathon.JavaView
import com.hack.hackathon.security.SecurityService
import com.hack.hackathon.vaadin.LocaleChanger
import com.hack.hackathon.view.*
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent
import com.vaadin.flow.component.Component
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.applayout.AppLayout
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.checkbox.Checkbox
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.menubar.MenuBar
import com.vaadin.flow.component.menubar.MenuBarVariant
import com.vaadin.flow.component.page.WebStorage
import com.vaadin.flow.component.tabs.Tab
import com.vaadin.flow.component.tabs.Tabs
import com.vaadin.flow.component.tabs.TabsVariant
import com.vaadin.flow.router.*
import com.vaadin.flow.server.VaadinSession
import com.vaadin.flow.server.auth.AnonymousAllowed
import com.vaadin.flow.theme.lumo.Lumo
import jakarta.annotation.security.PermitAll


@Route("")
@AnonymousAllowed
class MainLayout(
    private val securityService: SecurityService
) : AppLayout(), BeforeEnterObserver {
    init {
        val title = H1(getTranslation("app.title"))
        title.style
            .set("font-size", "var(--lumo-font-size-l)")
            .set("margin", "0 auto 0 0")["padding-left"] = "var(--lumo-space-s)"
        val tabs = tabs()
        setTheme()
        addToNavbar(title)
        addToNavbar(true, tabs)
        addToNavbar(dropDown())
    }

    private fun dropDown(): MenuBar {
        val menuBar = MenuBar()
        val themeToggle = Checkbox(getTranslation("app.header-box.dark-theme-toggle"))
        themeToggle.addValueChangeListener { e: ComponentValueChangeEvent<Checkbox?, Boolean> ->
            setTheme(e.value)
        }
        WebStorage.getItem("theme") { theme ->
            theme?.let {
                if (theme == "dark") {
                    themeToggle.value = true
                } else {
                    themeToggle.value = false
                }
                return@getItem
            }
            themeToggle.value = false
        }
        val localeChanger: LocaleChanger = LocaleChanger()
        val settings = menuBar.addItem(getTranslation("app.header-box.title"))
        val logoutBtn = Button(getTranslation("app.logout")) {
            securityService.logout()
        }
        val loginBtn = Button(getTranslation("app.login")) {
            UI.getCurrent().navigate(LoginPage::class.java)
        }
        logoutBtn.addThemeVariants(ButtonVariant.LUMO_ERROR)
        val registerBtn = Button(getTranslation("app.register")) {
            UI.getCurrent().navigate(RegisterPage::class.java)
        }
        val profileBtn = Button(getTranslation("app.profile")) {
            UI.getCurrent().navigate(UserView::class.java)
        }
        val subMenu = settings.subMenu
        subMenu.addItem(themeToggle)
        subMenu.addItem(localeChanger)
        securityService.authenticatedUser?.let {
            subMenu.addItem(logoutBtn)
            subMenu.addItem(profileBtn)
        } ?: let {
            subMenu.addItem(loginBtn)
            subMenu.addItem(registerBtn)
        }
        settings.addThemeNames()
        menuBar.addThemeVariants(MenuBarVariant.LUMO_END_ALIGNED)
        menuBar.style.set("margin", "0 0 0 auto")["padding-right"] = "var(--lumo-space-s)"
        return menuBar
    }

    private fun setTheme(isDark : Boolean) {
        val themeList = UI.getCurrent().element.themeList
        WebStorage.setItem("theme", if (isDark) "dark" else "light")
        if (isDark) {
            themeList.add(Lumo.DARK)
        } else {
            themeList.remove(Lumo.DARK)
        }
    }

    private fun setTheme() {
        val themeList = UI.getCurrent().element.themeList
        WebStorage.getItem("theme") { theme ->
            theme?.let {
                if (theme == "dark") {
                    WebStorage.setItem("theme", "dark")
                    themeList.add(Lumo.DARK)
                } else {
                    WebStorage.setItem("theme", "light")
                    themeList.remove(Lumo.DARK)
                }
            } ?: WebStorage.setItem("theme", "light")
        }
    }

    private fun tabs(): RouteTabs {
        val tabs = RouteTabs()
        //Табы TODO
        tabs.add(
            routerLink(getTranslation("app.main"), MainView::class.java),
            routerLink(getTranslation("app.map"), MapView::class.java),
            routerLink(getTranslation("app.vacancy"), VacancyView::class.java),
        )
        tabs.addThemeVariants(TabsVariant.LUMO_CENTERED)
        tabs.style["margin"] = "0 auto"
        return tabs
    }

    fun routerLink(title: String?, clazz: Class<out Component?>?): RouterLink {
        return RouterLink(title, clazz)
    }

    override fun beforeEnter(event: BeforeEnterEvent) {
        if (event.navigationTarget == MainLayout::class.java) {
            //TODO дефолтная навигация
            event.forwardTo(MainView::class.java)
        }
    }

    /**
     * Вкладки для корректного отображения текущей вкладки
     */
    private class RouteTabs : Tabs(), BeforeEnterObserver {
        private val routerLinkTabMap: MutableMap<RouterLink, Tab> = HashMap()

        fun add(vararg routerLink: RouterLink) {
            for (route in routerLink) {
                route.highlightCondition = HighlightConditions.sameLocation()
                route.highlightAction = HighlightAction { link: RouterLink?, shouldHighlight: Boolean ->
                    if (shouldHighlight) selectedTab = routerLinkTabMap[route]
                }
                routerLinkTabMap[route] = Tab(route)
                add(routerLinkTabMap[route])
            }
        }

        override fun beforeEnter(event: BeforeEnterEvent) {
            // если ни одна таба не будет совпадать
            selectedTab = null
        }
    }
}