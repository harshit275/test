# Secure Authorized Deduplication of data in Hybrid Cloud

Data deduplication is an effective compression technique to eliminate duplicate copies of data and has been widely used in cloud storage to reduce
the amount of storage space and save bandwidth. For the protection of
sensitive data while supporting deduplication, the convergent encryption
technique is used to encrypt the data before outsourcing to the cloud. To
make system more secure, the different users are again considered while
checking deduplication, but problem occurs in this approach if few contents of the two files are different then it will store them as two different
files on the cloud due to this cloud storage space reduces. The solution
on this problem is to use block level deduplication in this approach file
which want to store on cloud is divided into the number of blocks and
deduplication is performed on these blocks.
