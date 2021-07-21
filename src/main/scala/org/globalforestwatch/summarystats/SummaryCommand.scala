package org.globalforestwatch.summarystats

import cats.data.{NonEmptyList, Validated}
import com.monovore.decline.{Argument, Opts}
import cats.implicits._
import geotrellis.vector.{Feature, Geometry}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.globalforestwatch.features.{FeatureId, FeatureRDDFactory}
import org.globalforestwatch.util.Config

trait SummaryCommand {


  implicit val configArgument: Argument[Config] = new Argument[Config] {

    def read(string: String) = {
      string.split(":", 2) match {
        case Array(key, value) => Validated.valid(Config(key, value))
        case _ => Validated.invalidNel(s"Invalid key:value pair: $string")
      }
    }

    def defaultMetavar = "key:value"
  }


  val featuresOpt: Opts[NonEmptyList[String]] =
    Opts.options[String]("features", "URI of features in TSV format")

  val outputOpt: Opts[String] =
    Opts.option[String]("output", "URI of output dir for CSV files")

  val featureTypeOpt: Opts[String] = Opts
    .option[String](
      "feature_type",
      help = "Feature type: one of 'gadm', 'wdpa', 'geostore', 'gfwpro' or 'feature'"
    )
    .withDefault("feature")

  val splitFeatures: Opts[Boolean] = Opts
    .flag("split_features", "Split input features along 1x1 degree grid")
    .orFalse

  val limitOpt: Opts[Option[Int]] = Opts
    .option[Int]("limit", help = "Limit number of records processed")
    .orNone

  val isoFirstOpt: Opts[Option[String]] =
    Opts
      .option[String]("iso_first", help = "Filter by first letter of ISO code")
      .orNone

  val isoStartOpt: Opts[Option[String]] =
    Opts
      .option[String](
        "iso_start",
        help = "Filter by ISO code larger than or equal to given value"
      )
      .orNone

  val isoEndOpt: Opts[Option[String]] =
    Opts
      .option[String](
        "iso_end",
        help = "Filter by ISO code smaller than given value"
      )
      .orNone

  val isoOpt: Opts[Option[String]] =
    Opts.option[String]("iso", help = "Filter by country ISO code").orNone

  val admin1Opt: Opts[Option[String]] = Opts
    .option[String]("admin1", help = "Filter by country Admin1 code")
    .orNone

  val admin2Opt: Opts[Option[String]] = Opts
    .option[String]("admin2", help = "Filter by country Admin2 code")
    .orNone

  val idStartOpt: Opts[Option[Int]] =
    Opts
      .option[Int](
        "id_start",
        help = "Filter by IDs larger than or equal to given value"
      )
      .orNone

  val idEndOpt: Opts[Option[Int]] =
    Opts
      .option[Int]("id_end", help = "Filter by IDs smaller than given value")
      .orNone

  val iucnCatOpts: Opts[Option[NonEmptyList[String]]] =
    Opts
      .options[String]("iucn_cat", help = "Filter by IUCN Category")
      .orNone

  val wdpaStatusOpts: Opts[Option[NonEmptyList[String]]] =
    Opts
      .options[String]("wdpa_status", help = "Filter by WDPA Status")
      .orNone

  val tclOpt: Opts[Boolean] = Opts.flag("tcl", "TCL tile extent").orFalse

  val gladOpt: Opts[Boolean] = Opts.flag("glad", "GLAD tile extent").orFalse

  val fireAlertTypeOpt: Opts[String] = Opts
    .option[String]("fire_alert_type", help = "MODIS or VIIRS")
    .withDefault("VIIRS")

  val fireAlertSourceOpt: Opts[NonEmptyList[String]] = Opts
    .options[String](
      "fire_alert_source",
      help = "URI of fire alerts in TSV format"
    )

  val noOutputPathSuffixOpt: Opts[Boolean] = Opts.flag("no_output_path_suffix", help = "Do not autogenerate output path suffix at runtime").orFalse


  val pinnedVersionsOpts: Opts[Option[NonEmptyList[Config]]] = Opts.options[Config]("pin_version", "Pin version of contextual layer. Use syntax `--pin_version dataset:version`.").orNone

  val defaultOptions: Opts[(String, NonEmptyList[String], String, Boolean, Boolean, Option[NonEmptyList[Config]])] =
    (featureTypeOpt, featuresOpt, outputOpt, splitFeatures, noOutputPathSuffixOpt, pinnedVersionsOpts).tupled
  val fireAlertOptions: Opts[(String, NonEmptyList[String])] =
    (fireAlertTypeOpt, fireAlertSourceOpt).tupled

  val defaultFilterOptions: Opts[(Option[Int], Boolean, Boolean)] =
    (limitOpt, tclOpt, gladOpt).tupled
  val gdamFilterOptions: Opts[
    (Option[String],
      Option[String],
      Option[String],
      Option[String],
      Option[String],
      Option[String])
  ] = (isoOpt, isoFirstOpt, isoStartOpt, isoEndOpt, admin1Opt, admin2Opt).tupled
  val wdpaFilterOptions
  : Opts[(Option[NonEmptyList[String]], Option[NonEmptyList[String]])] =
    (wdpaStatusOpts, iucnCatOpts).tupled
  val featureFilterOptions: Opts[(Option[Int], Option[Int])] =
    (idStartOpt, idEndOpt).tupled

  def runAnalysis(analysis: String,
                  fType: String,
                  featureUris: NonEmptyList[String],
                  kwargs: Map[String, Any]): Unit = {

    val spark: SparkSession =
      SummarySparkSession(s"${analysis} Session")

    /* Transition from DataFrame to RDD in order to work with GeoTrellis features */
    val featureRDD: RDD[Feature[Geometry, FeatureId]] =
      FeatureRDDFactory(analysis, fType, featureUris, kwargs, spark)

    SummaryAnalysisFactory(analysis, featureRDD, fType, spark, kwargs).runAnalysis

    spark.stop
  }

}
