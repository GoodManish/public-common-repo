SELECT 
    CASE 
        WHEN LENGTH(description) <= 35 THEN description
        ELSE LEFT(description, 35) 
    END AS first_35_char,
    CASE 
        WHEN LENGTH(description) > 35 THEN SUBSTRING(description, 36) 
        ELSE NULL 
    END AS remaining_char
FROM your_table;
