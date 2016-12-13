from __future__ import print_function
import sys
sys.path.insert(1,"../../../")
from tests import pyunit_utils
import h2o
import threading


def h2oshutdown():
    """
    Python API test: h2o.shutdown(prompt=False)
    Deprecated, use h2o.cluster().shutdown()
    """
    thread = threading.Thread(target=call_shutdown)
    thread.daemon =True

    try:
        thread.start()
        thread.join(1.0)
    except Exception as e:
        assert False, "h2o.shutdown() command is not working."

def call_shutdown():
    h2o.shutdown(prompt=True)   # call shutdown but do not actually shut anything down.

if __name__ == "__main__":
    pyunit_utils.standalone_test(h2oshutdown)
else:
    h2oshutdown()
