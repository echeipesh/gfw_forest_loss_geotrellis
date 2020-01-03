# Tree Cover Loss Analysis

This project performs a polygonal summary on tree cover loss and intersecting layers for a given input feature using SPARK and Geotrellis.

## Analysis

Currently the following analysis are implemented
-   Tree Cover Loss
-   Annual Update
-   Annual Update minimal
-   Carbon Flux
-   Glad Alerts


### Tree Cover Loss
A simple analysis which only looks at Tree Cover Loss, Tree Cover Density (2000 or 2010) and optionally Primary Forest.
Users can select one or many tree cover thresholds. Output will be a flat file, with one row per input feature and three cover density threshold.

This type of analysis only supports simple features as input. Best used together with the [ArcPY Client](https://github.com/wri/gfw_forest_loss_geotrellis_arcpy_client).

```sbt
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis treecoverloss --feature_type simple --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix

``` 

### Annual Update
A complex analysis intersecting Tree Cover Loss data with more than 40 layers. This analysis is used for the GFU. Supported input features are GADM features only.
Output are Summary and Change tables for ISO, ADM1 and ADM2 areas.

```sbt
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis annualupdate --feature_type gadm --tcl --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix

```

### Annual Update minimal
This analysis follows the same methodology as the annual update analysis above, just with fewer intersecting layers. 
It is used to compute statistics for the GFW country and user dashboards.

Supported input features are
-   GADM
-   Geostore
-   WDPA
-   Simple Feature

Output are Whitelist, Summary and Change tables for input features. For GADM, there will be seperate sets for ISO, ADM1 and ADM2 areas.
For GADM there will also be summary tables with one row per ISO, ADM1, ADM2 and Tree Cover Density, which is used for to prepare the Download Spreatsheets on the GFW country pages.
To produce final spreadsheets you will need to add another [post processing step](https://github.com/wri/write_country_stats).


```sbt
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis annualupdate_minimal --feature_type gadm --tcl --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis annualupdate_minimal --feature_type wdpa --tcl --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis annualupdate_minimal --feature_type geostore --tcl --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis annualupdate_minimal --feature_type simple --tcl --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix

```

### Carbon Flux
Carbon Flux analysis is used to produce statistics for GFW climate topic pages.
It uses same approach as the annual update analysis, but with fewer and different input layers. It currently only works with GADM features.

```sbt
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis carbonflux --feature_type gadm --tcl --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix
 ```

### Glad Alerts
Glad alert analysis computes whitelist, summary, daily and weekly change data for given input features and intersects areas with the same contextual layers as in annual update minimal.
It is used to update the country and user dashboards for the GFW website.

Users can select, if they want to run the full analysis, or only look at change data. Computing only change data makes sense, if neither input features, nor contextual layers have changed, but only glad alerts.
In that case, only the daily and weekly change tables will be updated. 

Supported input features are
-   GADM
-   Geostore
-   WDPA
-   Simple Feature


```sbt
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis gladalerts --feature_type gadm --glad --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix [--change_only]
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis gladalerts --feature_type wdpa --glad --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix [--change_only]
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis gladalerts --feature_type geostore --glad --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix [--change_only]
sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --analysis gladalerts --feature_type simple --glad --features s3://bucket/prefix/file.tsv --output s3://bucket/prefix [--change_only]

```

## Inputs

Use Polygon Features encoded in TSV format. Geometries must be encoded in WKB. You can specify one or many input files using wildcards:

ex: 
-   `s3://bucket/prefix/gadm36_1_1.tsv`
-   `s3://bucket/prefix/geostore_*.tsv`

Make sure features are sufficiently small to assure a well balanced partition size and workload.
Larger features should be split into smaller features, prior to running the analysis. 
Also make sure, that features do not overlap with tile boundaries (we use 10x10 degree tiles). 
For best performance, intersect input features with a 1x1 degree grid.
If you are not sure how to best approach this, simply use the [ArcPY Client](https://github.com/wri/gfw_forest_loss_geotrellis_arcpy_client)

## Options
The following options are supported:

|Option| Type | Analysis or Feature Type | Description|
|-------|------|---------|------------|
|analysis| string | |Type of analysis to run [annualupdate, annualupdate_minimal,carbonflux,gladalerts,treecoverloss]|
|features| string | all (required) | URI of features in TSV format|
|output| string | all (required) | URI of output dir for CSV files|
|feature_type| string | all (required) | Feature type: one of 'gadm', 'wdpa', 'geostore' or 'feature|
|limit| int | all | Limit number of records processed|
|iso_first| string | for `gadm` or `wdpa` feature types | Filter by first letter of ISO code|
|iso_start| string | for `gadm` or `wdpa` feature types | Filter by ISO code larger than or equal to given value|
|iso_end| string | for `gadm` or `wdpa` feature types | Filter by ISO code smaller than given value|
|iso| string | for `gadm` or `wdpa` feature types | Filter by country ISO code|
|admin1| string | for `gadm` feature types | Filter by country Admin1 code|
|admin2| string | for `gadm` feature types | Filter by country Admin2 code|
|id_start| int | for `feature` feature types | Filter by IDs larger than or equal to given value|
|id_end| int | for `feature` feature types | Filter by IDs smaller than given value|
|iucn_cat| string | for `wdpa` feature types |Filter by IUCN Category|
|wdpa_status| string | for `wdpa` feature types |Filter by WDPA Status"|
|tcd| int | for `treecover` analysis |Select tree cover density year|
|threshold| int | for `treecover` analysis |Treecover threshold to apply|
|primary-forests| flag | for `treecover` analysis|Include Primary Forests|
|tcl| flag | all, requires boolean `tcl` field in input feature class |Filter input feature by TCL tile extent|
|glad| flag | all, requires boolean `glad` field in input feature class|GLAD tile extent|
|change_only| flag | all except `treecover loss |Process change only|
|build_data_cube| flag | `glad` |Build XYZ data cube|

## Inventory

-   [`build.sbt`](build.sbt): Scala Build Tool build configuration file
-   [`.sbtopts`](.sbtopts): Command line options for SBT, including JVM parameters
-   [`project`](project): Additional configuration for SBT project, plugins, utility, versions
-   [`src/main/scala`](src/main/scala): Application and utility code
-   [`src/test/scala`](src/test/scala): Unit test files

## Spark Job Commands

### Local

For local testing input should be limited with `--limit` flag to minimize the time.

```sbt
sbt:geotrellis-wri> test:runMain org.globalforestwatch.summarystats.SummaryMain --features file:/Users/input/ten-by-ten-gadm36/wdpa__10N_010E.tsv --output file:/User/out/summary --limit 10
```

### EMR

Before running review `sbtlighter` configuration in `build.sbt`, `reload` SBT session if modified.

```sbt
sbt:geotrellis-wri> sparkCreateCluster

sbt:treecoverloss> sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain --features s3://gfw-files/2018_update/tsv/gadm36_1_1.csv --output s3://gfw-files/2018_update/results/summary --feature_type gadm --analysis annualupdate_minimal --tcl

sbt:treecoverloss> sparkSubmitMain org.globalforestwatch.summarystats.SummaryMain  --features s3://gfw-files/2018_update/tsv/wdpa__*.tsv --output s3://gfw-files/2018_update/results/summary  --feature_type wdpa --analysis gladalerts --tcl --iso BRA
```