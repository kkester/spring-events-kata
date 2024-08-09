#!/bin/sh

while [ "$migration_type" != "V" ] && [ "$migration_type" != "R" ] && [ "$migration_type" != "U" ];
do
    echo "What type of migration V (Versioned), R (Repeatable), or U (Undo)?"
    read -r migration_type
    migration_type=$(echo "$migration_type" | tr '[:lower:]' '[:upper:]')
    if [ "$migration_type" != "V" ] && [ "$migration_type" != "R" ] && [ "$migration_type" != "U" ]; then
        printf "Migration type must be one of [V, R, U]\n"
    fi
done


while [ -z "$migration_name" ] || [ "$migration_name" = "" ]
do
    echo "What is the name of this migration?"
    read -r migration_name

    if [ "$migration_name" = "" ]; then
        printf "You must provide a name for this migration.\n"
    fi
done

migration_name="$migration_type""$(date +"%Y%m%d%H%M")"__"$migration_name".sql
touch "$migration_name"
echo "Generated: ""$migration_name"
mv "$migration_name" src/main/resources/db/migration
