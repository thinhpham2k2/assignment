export VAULT_TOKEN="0000"
export VAULT_ADDR="http://host.docker.internal:8200"

vault kv put secret/auth-service jwt.key=Abbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
vault kv put secret/core-service jwt.key=Abbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb
