package org.globalforestwatch.summarystats.annualupdate_minimal

import com.github.mrpowers.spark.daria.sql.DataFrameHelpers.validatePresenceOfColumns
import org.apache.spark.sql.functions.{length, max, sum}
import org.apache.spark.sql.{Column, DataFrame, SparkSession}

object AnnualUpdateMinimalDF {

  val contextualLayers = List(
    "umd_tree_cover_density__threshold",
    "tsc_tree_cover_loss_drivers__type",
    "esa_land_cover_2015__class",
    "is__birdlife_alliance_for_zero_extinction_site",
    "gfw_plantation__type",
    "is__gmw_mangroves_1996",
    "is__gmw_mangroves_2016",
    "ifl_intact_forest_landscape__year",
    "is__umd_regional_primary_forest_2001",
    "is__gfw_tiger_landscape",
    "is__landmark_land_right",
    "is__gfw_land_right",
    "is__birdlife_key_biodiversity_area",
    "is__gfw_mining",
    "is__peatland",
    "is__gfw_oil_palm",
    "is__idn_forest_moratorium",
    "is__gfw_wood_fiber",
    "is__gfw_resource_right",
    "is__gfw_managed_forest",
    "is__umd_tree_cover_gain_2000-2012"
  )

  def unpackValues(cols: List[Column],
                   wdpa: Boolean = false)(df: DataFrame): DataFrame = {

    val spark: SparkSession = df.sparkSession
    import spark.implicits._

    def defaultUnpackCols =
      List(
        $"data_group.lossYear" as "umd_tree_cover_loss__year",
        $"data_group.threshold" as "umd_tree_cover_density__threshold",
        $"data_group.drivers" as "tsc_tree_cover_loss_drivers__type",
        $"data_group.globalLandCover" as "esa_land_cover_2015__class",
        $"data_group.primaryForest" as "is__umd_regional_primary_forest_2001",
        $"data_group.aze" as "is__birdlife_alliance_for_zero_extinction_site",
        $"data_group.plantations" as "gfw_plantation__type",
        $"data_group.mangroves1996" as "is__gmw_mangroves_1996",
        $"data_group.mangroves2016" as "is__gmw_mangroves_2016",
        $"data_group.intactForestLandscapes" as "ifl_intact_forest_landscape__year",
        $"data_group.tigerLandscapes" as "is__gfw_tiger_landscape",
        $"data_group.landmark" as "is__landmark_land_right",
        $"data_group.landRights" as "is__gfw_land_right",
        $"data_group.keyBiodiversityAreas" as "is__birdlife_key_biodiversity_area",
        $"data_group.mining" as "is__gfw_mining",
        $"data_group.peatlands" as "is__peatland",
        $"data_group.oilPalm" as "is__gfw_oil_palm",
        $"data_group.idnForestMoratorium" as "is__idn_forest_moratorium",
        $"data_group.woodFiber" as "is__gfw_wood_fiber",
        $"data_group.resourceRights" as "is__gfw_resource_right",
        $"data_group.logging" as "is__gfw_managed_forest",
        $"data_group.isGain" as "is__umd_tree_cover_gain_2000-2012",
        $"data.treecoverExtent2000" as "umd_tree_cover_extent_2000__ha",
        $"data.treecoverExtent2010" as "umd_tree_cover_extent_2010__ha",
        $"data.totalArea" as "area__ha",
        $"data.totalGainArea" as "umd_tree_cover_gain_2000-2012__ha",
        $"data.totalBiomass" as "whrc_aboveground_biomass_stock_2000__Mg",
        $"data.treecoverLoss" as "umd_tree_cover_loss__ha",
        $"data.biomassLoss" as "whrc_aboveground_biomass_loss__Mg",
        $"data.co2Emissions" as "whrc_aboveground_co2_emissions__Mg",
        $"data.totalCo2" as "whrc_aboveground_co2_stock_2000__Mg",
        $"data.totalGrossCumulAbovegroundRemovalsCo2" as "gfw_gross_cumulative_aboveground_co2_removals__Mg",
        $"data.totalGrossCumulBelowgroundRemovalsCo2" as "gfw_gross_cumulative_belowground_co2_removals__Mg",
        $"data.totalGrossCumulAboveBelowgroundRemovalsCo2" as "gfw_gross_cumulative_aboveground_belowground_co2_removals__Mg",
        $"data.totalNetFluxCo2" as "gfw_net_flux_co2e__Mg",
        $"data.totalGrossEmissionsCo2eCo2Only" as "gfw_gross_emissions_co2e_co2_only__Mg",
        $"data.totalGrossEmissionsCo2eNonCo2" as "gfw_gross_emissions_co2e_non_co2__Mg",
        $"data.totalGrossEmissionsCo2e" as "gfw_gross_emissions_co2e_all_gases__Mg"
      )

    val unpackCols = {
      if (!wdpa) {
        defaultUnpackCols ::: List(
          $"data_group.wdpa" as "wdpa_protected_area__iucn_cat"
        )
      } else defaultUnpackCols
    }

    validatePresenceOfColumns(df, Seq("id", "data_group", "data"))

    df.select(cols ::: unpackCols: _*)
  }

  def aggSummary(groupByCols: List[String],
                 wdpa: Boolean = false)(df: DataFrame): DataFrame = {

    val cols =
      if (!wdpa)
        groupByCols ::: contextualLayers ::: List(
          "wdpa_protected_area__iucn_cat"
        )
      else groupByCols ::: contextualLayers

    df.groupBy(cols.head, cols.tail: _*)
      .agg(
        sum("umd_tree_cover_extent_2000__ha") as "umd_tree_cover_extent_2000__ha",
        sum("umd_tree_cover_extent_2010__ha") as "umd_tree_cover_extent_2010__ha",
        sum("area__ha") as "area__ha",
        sum("umd_tree_cover_gain_2000-2012__ha") as "umd_tree_cover_gain_2000-2012__ha",
        sum("whrc_aboveground_biomass_stock_2000__Mg") as "whrc_aboveground_biomass_stock_2000__Mg",
        sum("whrc_aboveground_co2_stock_2000__Mg") as "whrc_aboveground_co2_stock_2000__Mg",
        sum("umd_tree_cover_loss__ha") as "umd_tree_cover_loss__ha",
        sum("whrc_aboveground_biomass_loss__Mg") as "whrc_aboveground_biomass_loss__Mg",
        sum("gfw_gross_cumulative_aboveground_co2_removals__Mg") as "gfw_gross_cumulative_aboveground_co2_removals__Mg",
        sum("gfw_gross_cumulative_belowground_co2_removals__Mg") as "gfw_gross_cumulative_belowground_co2_removals__Mg",
        sum("gfw_gross_cumulative_aboveground_belowground_co2_removals__Mg") as "gfw_gross_cumulative_aboveground_belowground_co2_removals__Mg",
        sum("gfw_net_flux_co2e__Mg") as "gfw_net_flux_co2e__Mg",
        sum("gfw_gross_emissions_co2e_co2_only__Mg") as "gfw_gross_emissions_co2e_co2_only__Mg",
        sum("gfw_gross_emissions_co2e_non_co2__Mg") as "gfw_gross_emissions_co2e_non_co2__Mg",
        sum("gfw_gross_emissions_co2e_all_gases__Mg") as "gfw_gross_emissions_co2e_all_gases__Mg",
      )
  }

  def aggSummary2(groupByCols: List[String],
                  wdpa: Boolean = false)(df: DataFrame): DataFrame = {

    val cols =
      if (!wdpa)
        groupByCols ::: contextualLayers ::: List(
          "wdpa_protected_area__iucn_cat"
        )
      else groupByCols ::: contextualLayers

    df.groupBy(cols.head, cols.tail: _*)
      .agg(
        sum("umd_tree_cover_extent_2000__ha") as "umd_tree_cover_extent_2000__ha",
        sum("umd_tree_cover_extent_2010__ha") as "umd_tree_cover_extent_2010__ha",
        sum("area__ha") as "area__ha",
        sum("umd_tree_cover_gain_2000-2012__ha") as "umd_tree_cover_gain_2000-2012__ha",
        sum("whrc_aboveground_biomass_stock_2000__Mg") as "whrc_aboveground_biomass_stock_2000__Mg",
        sum("whrc_aboveground_co2_stock_2000__Mg") as "whrc_aboveground_co2_stock_2000__Mg",
        sum("umd_tree_cover_loss__ha") as "umd_tree_cover_loss__ha",
        sum("whrc_aboveground_biomass_loss__Mg") as "whrc_aboveground_biomass_loss__Mg",
        sum("gfw_gross_cumulative_aboveground_co2_removals__Mg") as "gfw_gross_cumulative_aboveground_co2_removals__Mg",
        sum("gfw_gross_cumulative_belowground_co2_removals__Mg") as "gfw_gross_cumulative_belowground_co2_removals__Mg",
        sum("gfw_gross_cumulative_aboveground_belowground_co2_removals__Mg") as "gfw_gross_cumulative_aboveground_belowground_co2_removals__Mg",
        sum("gfw_net_flux_co2e__Mg") as "gfw_net_flux_co2e__Mg",
        sum("gfw_gross_emissions_co2e_co2_only__Mg") as "gfw_gross_emissions_co2e_co2_only__Mg",
        sum("gfw_gross_emissions_co2e_non_co2__Mg") as "gfw_gross_emissions_co2e_non_co2__Mg",
        sum("gfw_gross_emissions_co2e_all_gases__Mg") as "gfw_gross_emissions_co2e_all_gases__Mg",
      )
  }

  def aggChange(groupByCols: List[String],
                wdpa: Boolean = false)(df: DataFrame): DataFrame = {

    val cols =
      if (!wdpa)
        groupByCols ::: List("umd_tree_cover_loss__year") ::: contextualLayers ::: List(
          "wdpa_protected_area__iucn_cat"
        )
      else groupByCols ::: List("umd_tree_cover_loss__year") ::: contextualLayers

    df.groupBy(cols.head, cols.tail: _*)
      .agg(
        sum("umd_tree_cover_loss__ha") as "umd_tree_cover_loss__ha",
        sum("whrc_aboveground_biomass_loss__Mg") as "whrc_aboveground_biomass_loss__Mg",
        sum("whrc_aboveground_co2_emissions__Mg") as "whrc_aboveground_co2_emissions__Mg",
        sum("gfw_gross_emissions_co2e_co2_only__Mg") as "gfw_gross_emissions_co2e_co2_only__Mg",
        sum("gfw_gross_emissions_co2e_non_co2__Mg") as "gfw_gross_emissions_co2e_non_co2__Mg",
        sum("gfw_gross_emissions_co2e_all_gases__Mg") as "gfw_gross_emissions_co2e_all_gases__Mg"
      )
  }

  def whitelist(groupByCols: List[String],
                wdpa: Boolean = false)(df: DataFrame): DataFrame = {

    val spark = df.sparkSession
    import spark.implicits._

    val defaultAggCols = List(
      max(length($"tsc_tree_cover_loss_drivers__type")).cast("boolean") as "tsc_tree_cover_loss_drivers__type",
      max(length($"esa_land_cover_2015__class"))
        .cast("boolean") as "esa_land_cover_2015__class",
      max($"is__umd_regional_primary_forest_2001") as "is__umd_regional_primary_forest_2001",
      max($"is__birdlife_alliance_for_zero_extinction_site") as "is__birdlife_alliance_for_zero_extinction_site",
      max(length($"gfw_plantation__type"))
        .cast("boolean") as "gfw_plantation__type",
      max($"is__gmw_mangroves_1996") as "is__gmw_mangroves_1996",
      max($"is__gmw_mangroves_2016") as "is__gmw_mangroves_2016",
      max(length($"ifl_intact_forest_landscape__year"))
        .cast("boolean") as "ifl_intact_forest_landscape__year",
      max($"is__gfw_tiger_landscape") as "is__gfw_tiger_landscape",
      max($"is__landmark_land_right") as "is__landmark_land_right",
      max($"is__gfw_land_right") as "is__gfw_land_right",
      max($"is__birdlife_key_biodiversity_area") as "is__birdlife_key_biodiversity_area",
      max($"is__gfw_mining") as "is__gfw_mining",
      max($"is__peatland") as "is__peatland",
      max($"is__gfw_oil_palm") as "is__gfw_oil_palm",
      max($"is__idn_forest_moratorium") as "is__idn_forest_moratorium",
      max($"is__gfw_wood_fiber") as "is__gfw_wood_fiber",
      max($"is__gfw_resource_right") as "is__gfw_resource_right",
      max($"is__gfw_managed_forest") as "is__gfw_managed_forest",
      max($"is__umd_tree_cover_gain_2000-2012") as "is__umd_tree_cover_gain_2000-2012"
    )

    val aggCols =
      if (!wdpa)
        defaultAggCols ::: List(
          max(length($"wdpa_protected_area__iucn_cat"))
            .cast("boolean") as "wdpa_protected_area__iucn_cat"
        )
      else defaultAggCols

    df.groupBy(groupByCols.head, groupByCols.tail: _*)
      .agg(aggCols.head, aggCols.tail: _*)

  }

  def whitelist2(groupByCols: List[String],
                 wdpa: Boolean = false)(df: DataFrame): DataFrame = {

    val spark = df.sparkSession
    import spark.implicits._

    val defaultAggCols: List[Column] = List(
      max($"tsc_tree_cover_loss_drivers__type") as "tsc_tree_cover_loss_drivers__type",
      max($"esa_land_cover_2015__class") as "esa_land_cover_2015__class",
      max($"is__umd_regional_primary_forest_2001") as "is__umd_regional_primary_forest_2001",
      max($"is__birdlife_alliance_for_zero_extinction_site") as "is__birdlife_alliance_for_zero_extinction_site",
      max($"gfw_plantation__type") as "gfw_plantation__type",
      max($"is__gmw_mangroves_1996") as "is__gmw_mangroves_1996",
      max($"is__gmw_mangroves_2016") as "is__gmw_mangroves_2016",
      max($"ifl_intact_forest_landscape__year") as "ifl_intact_forest_landscape__year",
      max($"is__gfw_tiger_landscape") as "is__gfw_tiger_landscape",
      max($"is__landmark_land_right") as "is__landmark_land_right",
      max($"is__gfw_land_right") as "is__gfw_land_right",
      max($"is__birdlife_key_biodiversity_area") as "is__birdlife_key_biodiversity_area",
      max($"is__gfw_mining") as "is__gfw_mining",
      max($"is__peatland") as "is__peatland",
      max($"is__gfw_oil_palm") as "is__gfw_oil_palm",
      max($"is__idn_forest_moratorium") as "is__idn_forest_moratorium",
      max($"is__gfw_wood_fiber") as "is__gfw_wood_fiber",
      max($"is__gfw_resource_right") as "is__gfw_resource_right",
      max($"is__gfw_managed_forest") as "is__gfw_managed_forest",
      max($"is__umd_tree_cover_gain_2000-2012") as "is__umd_tree_cover_gain_2000-2012"
    )

    val aggCols = if (!wdpa)
      defaultAggCols ::: List(
        max($"wdpa_protected_area__iucn_cat") as "wdpa_protected_area__iucn_cat"
      )
    else defaultAggCols

    df.groupBy(groupByCols.head, groupByCols.tail: _*)
      .agg(aggCols.head, aggCols.tail: _*)

  }
}
