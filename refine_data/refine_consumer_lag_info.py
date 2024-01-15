import pandas as pd
import re


# Function to process data chunks
def process_data_chunks(data):
    # Split the data into chunks of 15 lines
    chunks = [data[i:i + 15] for i in range(0, len(data), 15)]
    processed_data = []

    for chunk in chunks:
        # Get the timestamp from the first line
        timestamp_match = re.search(r"(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2})", chunk[0])
        if timestamp_match:
            timestamp = timestamp_match.group(1)
        else:
            continue  # If timestamp is not found, skip this chunk

        # Process partition information lines
        for line in chunk[3:]:  # Start from the 4th line of the chunk
            partition_info_match = re.search(
                r"\s*(\w+)\s+(\w+)\s+(\d+)\s+(\d+)\s+(\d+)\s+(\d+)\s+([\w-]+)\s+(\/\d+\.\d+\.\d+\.\d+)",
                line)
            if partition_info_match:
                processed_data.append({
                    'TIMESTAMP': timestamp,
                    'GROUP': partition_info_match.group(1),
                    'TOPIC': partition_info_match.group(2),
                    'PARTITION': int(partition_info_match.group(3)),
                    'CURRENT-OFFSET': int(partition_info_match.group(4)),
                    'LOG-END-OFFSET': int(partition_info_match.group(5)),
                    'LAG': int(partition_info_match.group(6)),
                    'CONSUMER-ID': partition_info_match.group(7),
                    # Append timestamp to consumer ID
                    'HOST': partition_info_match.group(8)[1:]
                })

    return processed_data


# Load the data from the uploaded text file
file_path = 'consumer_info.txt'
data = []
with open(file_path, 'r') as file:
    data = file.readlines()

# Process the data and convert to DataFrame
processed_partition_data = process_data_chunks(data)
df_processed_partitions = pd.DataFrame(processed_partition_data)

# Save the processed DataFrame to an Excel file
excel_file_path_processed = 'consumer_info.xlsx'
df_processed_partitions.to_excel(excel_file_path_processed, index=False)
