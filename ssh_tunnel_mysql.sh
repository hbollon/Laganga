#!/bin/bash
# Ouvre un tunnel SSH pour pouvoir se connecter à la base de données MySQL
ssh -L 3306:localhost:3306 l2_gr2@os-vps418.infomaniak.ch -p 522
