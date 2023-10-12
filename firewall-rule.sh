#!/bin/bash

# Set the SOURCE_IP environment variable
export SOURCE_IP=10.0.0.1

# Run the PyInfra script
pyinfra configure_iptables.py

----OR------
#!/bin/bash

# Set the SOURCE_IP environment variable
export SOURCE_IP=10.0.0.1

# Specify the full path to the PyInfra script
PYINFRA_SCRIPT_PATH="/path/to/your/iptables/configure_iptables.py"

# Run the PyInfra script
pyinfra "$PYINFRA_SCRIPT_PATH"




----------------------NOTE------------------
# Before running above script, please make sure that file has the executables rights.
chmod +x firewall-rules-prod.sh
