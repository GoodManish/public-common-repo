#!/bin/bash
# Switch to prod cluster
kubectl config use-context prod-cluster

# Set namespace to production
kubectl config set-context --current --namespace=production

# Show a big red warning banner
echo -e "\033[41;37m*** WARNING: You are now on PROD cluster! ***\033[0m"

# Change prompt to red for this session
export PS1="\[\033[31m\]\u@\h:\w\$ \[\033[0m\]"

# Start a new shell so the red prompt stays active
bash
