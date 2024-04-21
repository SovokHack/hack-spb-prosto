package com.hack.hackathon.view

import com.hack.hackathon.layout.MainLayout
import com.hack.hackathon.service.SpecializationService
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.page.WebStorage
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.Route
import org.json.JSONArray
import org.json.JSONObject

@Route("vacancy", layout = MainLayout::class)
class VacancyView(
    private val specializationService: SpecializationService
) : VerticalLayout() {

    init {
        val experienceFilter = ComboBox<String>()
        val scheduleFilter = ComboBox<String>()
        val employmentTypeFilter = ComboBox<String>()
        val filters = Filters()
        WebStorage.getItem("experienceFilter") { if (it != null) experienceFilter.value = it }
        WebStorage.getItem("scheduleFilter") { if (it != null) scheduleFilter.value = it }
        WebStorage.getItem("employmentTypeFilter") { if (it != null) employmentTypeFilter.value = it }
        experienceFilter.addValueChangeListener {
            WebStorage.setItem("experienceFilter", it.value)
        }
        scheduleFilter.addValueChangeListener {
            WebStorage.setItem("scheduleFilter", it.value)
        }
        employmentTypeFilter.addValueChangeListener {
            WebStorage.setItem("employmentTypeFilter", it.value)
        }
        val binder = Binder(Filters::class.java)
        binder.bean = filters
        /*binder.forField(experienceFilter).bind({it.experience},{it, value -> it.experience = value})
        binder.forField(employmentTypeFilter).bind({it.employmentType}, {it, value -> it.employmentType = value})
        binder.forField(scheduleFilter).bind({it.schedule}, {it, value -> it.schedule = value})*/

        val json = JSONArray()
        val list = mutableListOf<HorizontalLayout>()
        for (i in 0 until  json.length()) {
            val obj = json.getJSONObject(i)
            val verticalLayout = VerticalLayout()
            verticalLayout.add(H3(obj.getString("name")))
            verticalLayout.add(Div(obj.getString("description")))
        }


    }
}

class Filters(
    var experience : Int? = null,
    var schedule : Int? = null,
    var employmentType : Int? = null,
)