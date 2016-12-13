from __future__ import print_function
import sys
sys.path.insert(1,"../../../")
from tests import pyunit_utils
import h2o
import threading

def h2ocluster_shutdown():
    """
    Python API test: h2o.cluster().shutdown(prompt=False)
    """
    thread = threading.Thread(target=call_shutdown)
    thread.daemon =True

    try:
        thread.start()
        thread.join(1.0)
    except Exception as e:
        assert False, "h2o.cluster().shutdown() command is not working."

def call_shutdown():
    h2o.cluster().shutdown(prompt=True)   # call shutdown but do not actually shut anything down.

if __name__ == "__main__":
    pyunit_utils.standalone_test(h2ocluster_shutdown)
else:
    h2ocluster_shutdown()
