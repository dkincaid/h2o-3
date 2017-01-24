from __future__ import print_function
from builtins import range
import sys
sys.path.insert(1,"../../../")
import h2o
from tests import pyunit_utils
from h2o.transforms.decomposition import H2OPCA



def pca_car():
  num_runs = 10
  run_time_c = []
  run_time_c2 = []

  for run_index in range(num_runs):  # multiple runs to get an idea of run time info
    car = h2o.upload_file(pyunit_utils.locate("smalldata/pca_test/car.arff.txt"))
    carPCA = H2OPCA(k=car.ncols, transform="STANDARDIZE")
    carPCA.train(x=list(range(0, car.ncols)), training_frame=car)
    run_time_c.append(carPCA._model_json['output']['end_time']-carPCA._model_json['output']['start_time'])
    print("PCA model training time with car.arff.txt data in ms is {0}".format(run_time_c[run_index]))

    # this part will not run because it contains customer data on my personal machine.
    # corning = h2o.upload_file(pyunit_utils.locate("smalldata/pca_test/corning_munged_v1.csv"))
    # corningPCA = H2OPCA(k=228, transform="STANDARDIZE")
    # corningPCA.train(x=list(range(0, corning.ncols)), training_frame=corning)
    # run_time_c2.append(corningPCA._model_json['output']['end_time']-corningPCA._model_json['output']['start_time'])
    # print("PCA model training time with corning_munged_v1 data in ms is {0}".format(run_time_c2[run_index]))
    # print("For corning_munged_v1.csv, Mean run time in ms is {0}.  Max run time in ms is {1}.  Min run time in ms is {2}".format(
    # sum(run_time_c2)/len(run_time_c2), max(run_time_c2), min(run_time_c2)))

    h2o.remove_all()

  assert (max(run_time_c)) < 1000, "PCA runs for car.arff.txt take too much time!"

if __name__ == "__main__":
  pyunit_utils.standalone_test(pca_car)
else:
  pca_car()
