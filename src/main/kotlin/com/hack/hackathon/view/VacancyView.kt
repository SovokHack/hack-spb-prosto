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
        val experienceFilter = ComboBox<VacancyExperience>("Опыт")
        val scheduleFilter = ComboBox<VacancySchedule>("График")
        val employmentTypeFilter = ComboBox<VacancyEmploymentType>("Тип занятости")
        experienceFilter.setItemLabelGenerator { return@setItemLabelGenerator it.viewName }
        scheduleFilter.setItemLabelGenerator { return@setItemLabelGenerator it.viewName }
        employmentTypeFilter.setItemLabelGenerator { return@setItemLabelGenerator it.viewName }

        WebStorage.getItem("experienceFilter") { if (it != null) experienceFilter.value = VacancyExperience.valueOf(it) }
        WebStorage.getItem("scheduleFilter") { if (it != null) scheduleFilter.value = VacancySchedule.valueOf(it) }
        WebStorage.getItem("employmentTypeFilter") { if (it != null) employmentTypeFilter.value = VacancyEmploymentType.valueOf(it) }
        experienceFilter.addValueChangeListener {
            filters.experience = it.value
            WebStorage.setItem("experienceFilter", it.value.name)
            setItems()
        }

        experienceFilter.setItems(VacancyExperience.entries)
        scheduleFilter.setItems(VacancySchedule.entries)
        employmentTypeFilter.setItems(VacancyEmploymentType.entries)
        scheduleFilter.addValueChangeListener {
            filters.schedule = it.value
            WebStorage.setItem("scheduleFilter", it.value.name)
            setItems()
        }
        employmentTypeFilter.addValueChangeListener {
            filters.employmentType = it.value
            WebStorage.setItem("employmentTypeFilter", it.value.name)
            setItems()
        }
        grid.setSizeFull()
        grid.addComponentColumn { VacancyCardView(it) }
        val layout = HorizontalLayout(employmentTypeFilter, scheduleFilter, experienceFilter)
        add(layout)
        add(grid)


    }

    fun setItems() {
        grid.setItems( vacancyService.getVacancies(specializationService.specialization, filters.employmentType,filters.experience, filters.schedule).toList().map { it as JSONObject })
    }
}

class Filters(
    var experience : VacancyExperience? = null,
    var schedule : VacancySchedule? = null,
    var employmentType : VacancyEmploymentType? = null,
)