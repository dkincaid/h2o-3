setwd(normalizePath(dirname(R.utils::commandArgs(asValues=TRUE)$"f")))
source("../../../scripts/h2o-r-test-setup.R")

# Test PCA on car.arff.txt
test.pca.car <- function() {
  run_time_c = c()
  run_time_c2 = c()
  num_run = 10
  
  for (runIndex in 1:num_run) {
    Log.info("Importing car.arff.txt data...\n")
    data = h2o.uploadFile(locate("smalldata/pca_test/car.arff.txt"))
    mm = h2o.prcomp(data,transform = "STANDARDIZE",k=ncol(data))
    print("PCA run time with car.arff.txt data in ms is ")
    print(mm@model$run_time)
    run_time_c = c(run_time_c,mm@model$run_time)
    print("average run time in ms for data.arff.txt is: ")
    print(mean(run_time_c))
    print("maximum run time in ms for data.arff.txt is: ")
    print(max(run_time_c))
    print("minimum run time in ms for data.arff.txt is: ")
    print(min(run_time_c))


  # comment out this part before submitting to Jenkins.  Dataset is private.
#    Log.info("Importing corning data...\n")
#    data = h2o.uploadFile(locate("smalldata/pca_test/corning_munged_v1.csv"))
#    mm = h2o.prcomp(data,transform = "STANDARDIZE",k=228)
#    print("PCA run time with corning_munged_v1.csv data in ms is ")
#    print(mm@model$run_time)
#    run_time_c2 = c(run_time_c2,mm@model$run_time)
#    print("average run time in ms for corning_munged_v1.csv is: ")
#    print(mean(run_time_c2))
#    print("maximum run time in ms for corning_munged_v1.csv is: ")
#    print(max(run_time_c2))
#    print("minimum run time in ms for corning_munged_v1.csv is: ")
#    print(min(run_time_c2))

    h2o.removeAll()
  }
  # check to make sure PCA is not taking too long to run
  expect_true(max(run_time_c) < 1000)
}

doTest("PCA Test: USArrests Data", test.pca.car)
