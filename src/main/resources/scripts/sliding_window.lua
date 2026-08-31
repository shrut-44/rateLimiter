local key = KEYS[1]

local limit = tonumber(ARGV[1])
local windowSize = tonumber(ARGV[2])
local now = tonumber(ARGV[3])

local previousCount = tonumber(redis.call('HGET', key, 'previousCount') or '0')
local currentCount = tonumber(redis.call('HGET', key, 'currentCount') or '0')
local windowStart = tonumber(redis.call('HGET', key, 'windowStart') or now)

local elapsed = now - windowStart
local elapsedWindows = math.floor(elapsed / windowSize)

if elapsedWindows >= 1 then

    if elapsedWindows == 1 then
        previousCount = currentCount
    else
        previousCount = 0
    end

    currentCount = 0
    windowStart = windowStart + (elapsedWindows * windowSize)

end

local timeInCurrent = now - windowStart

local currentWeight = timeInCurrent / windowSize
local previousWeight = 1 - currentWeight

local estimatedCount =
    currentCount * currentWeight +
    previousCount * previousWeight

if estimatedCount >= limit then
    return 0
end

currentCount = currentCount + 1

redis.call('HSET', key,
    'previousCount', previousCount,
    'currentCount', currentCount,
    'windowStart', windowStart
)

redis.call('EXPIRE', key, windowSize * 2)

return 1