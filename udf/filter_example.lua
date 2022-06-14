local function map_profile(record)
    -- Add user and password to returned map.
    -- Could add other record bins here as well.
    return map {bin1=record.bin1, bin2=record.bin2}
end

function profile_filter(stream, invalue)
    local function filter_bin9(record)
        return record.bin9 == invalue
    end
    return stream: filter(filter_bin9): map(map_profile)
end