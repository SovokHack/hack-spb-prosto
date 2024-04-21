package com.hack.hackathon.view

import com.hack.hackathon.enumeration.VacancyEmploymentType
import com.hack.hackathon.enumeration.VacancyExperience
import com.hack.hackathon.enumeration.VacancySchedule
import com.hack.hackathon.layout.MainLayout
import com.hack.hackathon.service.SpecializationService
import com.hack.hackathon.service.VacancyService
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.page.WebStorage
import com.vaadin.flow.router.Route
import jakarta.annotation.security.PermitAll
import org.json.JSONArray
import org.json.JSONObject

@PermitAll
@Route("vacancy", layout = MainLayout::class)
class VacancyView(
    private val specializationService: SpecializationService,
    private val vacancyService: VacancyService,
) : VerticalLayout() {
    val grid = Grid<JSONObject>(JSONObject::class.java, false)
    val filters = Filters()
    init {
        val experienceFilter = ComboBox<VacancyExperience>(getTranslation("app.vacancy.experience"))
        val scheduleFilter = ComboBox<VacancySchedule>(getTranslation("app.vacancy.schedule"))
        val employmentTypeFilter = ComboBox<VacancyEmploymentType>(getTranslation("app.vacancy.employment.type"))
        experienceFilter.setItemLabelGenerator { return@setItemLabelGenerator it.viewName }
        scheduleFilter.setItemLabelGenerator { return@setItemLabelGenerator it.viewName }
        employmentTypeFilter.setItemLabelGenerator { return@setItemLabelGenerator it.viewName }

        WebStorage.getItem("experienceFilter") { if (it != null && it.isNotBlank()) experienceFilter.value = VacancyExperience.valueOf(it) }
        WebStorage.getItem("scheduleFilter") { if (it != null && it.isNotBlank()) scheduleFilter.value = VacancySchedule.valueOf(it) }
        WebStorage.getItem("employmentTypeFilter") { if (it != null && it.isNotBlank()) employmentTypeFilter.value = VacancyEmploymentType.valueOf(it) }
        experienceFilter.addValueChangeListener {
            filters.experience = it.value
            WebStorage.setItem("experienceFilter", it.value?.name)
            setItems()
        }

        experienceFilter.setItems(VacancyExperience.entries)
        scheduleFilter.setItems(VacancySchedule.entries)
        employmentTypeFilter.setItems(VacancyEmploymentType.entries)
        scheduleFilter.addValueChangeListener {
            filters.schedule = it.value
            WebStorage.setItem("scheduleFilter", it.value?.name)
            setItems()
        }
        employmentTypeFilter.addValueChangeListener {
            filters.employmentType = it.value
            WebStorage.setItem("employmentTypeFilter", it.value?.name)
            setItems()
        }
        grid.addComponentColumn { VacancyCardView(it) }.setWidth("90vw")
        val verticalLayout = VerticalLayout()
        val layout = HorizontalLayout(employmentTypeFilter, scheduleFilter, experienceFilter)
        verticalLayout.add(layout, grid)
        add(verticalLayout)


    }

    fun setItems() {
        val list = vacancyService.getVacancies(specializationService.specialization, filters.employmentType,filters.experience, filters.schedule).toListed()
        grid.setItems(list )
    }
}

class Filters(
    var experience : VacancyExperience? = null,
    var schedule : VacancySchedule? = null,
    var employmentType : VacancyEmploymentType? = null,
)

fun JSONArray.toListed() : List<JSONObject> {
    val list = mutableListOf<JSONObject>()
    for (i in 0 until length()) {
        list.add(this.getJSONObject(i))
    }
    return list
}