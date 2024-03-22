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

SELECT 
    a.*,
    CASE 
        WHEN LENGTH(a.shrt_name) <= 35 THEN a.shrt_name
        ELSE LEFT(a.shrt_name, 35) 
    END AS first_35_char,
    CASE 
        WHEN LENGTH(a.shrt_name) > 35 THEN SUBSTRING(a.shrt_name, 36) 
        ELSE NULL 
    END AS remaining_char
FROM 
    account a;
